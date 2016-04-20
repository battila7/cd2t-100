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

  private Map<String, List<LineNumberedException>> codeExceptionMap;

  private Map<String, Exception> nodeExceptionMap;

  private EmulatorState emulatorState;

  private final EmulatorObserver emulatorObserver;

  private EmulatorCycle currentCycle;

  public Emulator(EmulatorObserver emulatorObserver, long clockFrequency) {
    this.emulatorObserver = emulatorObserver;

    emulatorObserver.setEmulator(this);

    this.clockFrequency = clockFrequency;

    clockSignalTimer = new Timer();

    cycleDataQueue = new SynchronousQueue<>();

    nodes = new HashMap<>();

    communicationPorts = new ArrayList<>();

    emulatorState = EmulatorState.STOPPED;
  }

  public EmulatorState getState() {
    synchronized (emulatorState) {
      return emulatorState;
    }
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

  /* package */ void start(ExecutionMode executionMode) {
    currentCycle = new EmulatorCycle(executionMode);

    clockSignalTimer.scheduleAtFixedRate(
      currentCycle, 0, clockFrequency);
  }

  public Map<String, List<LineNumberedException>> getCodeExceptionMap() {
    return codeExceptionMap;
  }

  public Map<String, Exception> getNodeExceptionMap() {
    return nodeExceptionMap;
  }


  /* package */ void stop() {
    if (currentCycle != null) {
      currentCycle.cancel();

      currentCycle = null;
    }

    reset();
  }

  private void reset() {
    for (CommunicationPort port : communicationPorts) {
      port.reset();
    }

    for (Node node : nodes.values()) {
      node.reset();
    }
  }

  /* package */ void setState(EmulatorState emulatorState) {
    synchronized (emulatorState) {
      this.emulatorState = emulatorState;
    }

    emulatorObserver.onStateChanged(emulatorState);
  }

  /* package */ StateChangeRequest generateInstructions() {
    nodeExceptionMap = new HashMap<>();

    codeExceptionMap = new HashMap<>();

    for (Node node : nodes.values()) {
      List<LineNumberedException> exceptions;

      try {
        exceptions = node.buildCodeElementSet();
      } catch(SourceCodeFormatException e) {
        nodeExceptionMap.put(node.getGlobalName(), e);

        continue;
      }

      if (!exceptions.isEmpty()) {
        codeExceptionMap.put(node.getGlobalName(), exceptions);

        continue;
      }

      try {
        exceptions = node.buildInstructions();
      } catch (NodeExecutionException e) {
        nodeExceptionMap.put(node.getGlobalName(), e);

        continue;
      }

      if (!exceptions.isEmpty()) {
        codeExceptionMap.put(node.getGlobalName(), exceptions);
      }
    }

    if (!codeExceptionMap.isEmpty() || !nodeExceptionMap.isEmpty()) {
      return StateChangeRequest.ERROR;
    }

    return StateChangeRequest.RUN;
  }

  private class EmulatorCycle extends TimerTask {
    private final ExecutionMode executionMode;

    public EmulatorCycle(ExecutionMode executionMode) {
      this.executionMode = executionMode;
    }

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

      if (executionMode == ExecutionMode.STEPPED) {
        cancel();

        request(StateChangeRequest.PAUSE);
      }
    }
  }
}
