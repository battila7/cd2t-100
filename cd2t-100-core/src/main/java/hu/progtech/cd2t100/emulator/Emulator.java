package hu.progtech.cd2t100.emulator;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.NodeExecutionException;
import hu.progtech.cd2t100.computation.SourceCodeFormatException;
import hu.progtech.cd2t100.computation.NodeMemento;

import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class Emulator {
  private Timer clockSignalTimer;

  private BlockingQueue<EmulatorCycleData> cycleDataQueue;

  private long clockFrequency;

  private HashMap<String, Node> nodes;

  private ArrayList<CommunicationPort> communicationPorts;

  private Map<String, List<LineNumberedException>> exceptionMap;

  private EmulatorState emulatorState;

  public Emulator(long clockFrequency) {
    this.clockFrequency = clockFrequency;

    cycleDataQueue = new SynchronousQueue<>();

    nodes = new HashMap<>();

    communicationPorts = new ArrayList<>();

    emulatorState = EmulatorState.STOPPED;

    exceptionMap = new HashMap<>();
  }

  public synchronized EmulatorState getState() {
    return emulatorState;
  }

  public void request(StateChangeRequest stateChangeRequest) {
    emulatorState.onRequest(this, stateChangeRequest);
  }

  public long getClockFrequency() {
    return clockFrequency;
  }

  public BlockingQueue<EmulatorCycleData> getCycleDataQueue() {
    return cycleDataQueue;
  }

  public void start() {
    clockSignalTimer = new Timer();

    clockSignalTimer.scheduleAtFixedRate(
      new EmulatorCycle(), 0, clockFrequency);
  }

  public Map<String, List<LineNumberedException>> getExceptionMap() {
    return exceptionMap;
  }

  /* package */ void setState(EmulatorState emulatorState) {
    this.emulatorState = emulatorState;
  }

  public Map<String, Exception> generateInstructions() throws InvalidStateException {
    if (emulatorState != EmulatorState.STOPPED) {
      throw new InvalidStateException("Can only generate instructions in STOPPED state.");
    }

    HashMap<String, Exception> nodeExceptionMap = new HashMap<>();

    exceptionMap.clear();

    for (Node node : nodes.values()) {
      List<LineNumberedException> exceptions;

      try {
        exceptions = node.buildCodeElementSet();
      } catch(SourceCodeFormatException e) {
        nodeExceptionMap.put(node.getGlobalName(), e);

        continue;
      }

      if (!exceptions.isEmpty()) {
        exceptionMap.put(node.getGlobalName(), exceptions);

        continue;
      }

      try {
        exceptions = node.buildInstructions();
      } catch (NodeExecutionException e) {
        nodeExceptionMap.put(node.getGlobalName(), e);

        continue;
      }

      if (!exceptions.isEmpty()) {
        exceptionMap.put(node.getGlobalName(), exceptions);
      }
    }

    if (!exceptionMap.isEmpty() || !nodeExceptionMap.isEmpty()) {
      request(StateChangeRequest.ERROR);
    } else {
      request(StateChangeRequest.DEFAULT);
    }

    return nodeExceptionMap;
  }

  private class EmulatorCycle extends TimerTask {
    @Override
    public void run() {
      EmulatorCycleData data = new EmulatorCycleData();

      for (CommunicationPort cp : communicationPorts) {
        cp.step();

        data.addPortValue(cp);
      }

      for (Node n : nodes.values()) {
        try {
          n.step();

          data.addNodeMemento(n.saveToMemento());
        } catch (NodeExecutionException e) {

        }
      }

      try {
        cycleDataQueue.put(data);
      } catch (InterruptedException e) {

      }
    }
  }
}
