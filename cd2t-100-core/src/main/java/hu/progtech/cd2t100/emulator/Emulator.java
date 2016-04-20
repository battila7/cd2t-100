package hu.progtech.cd2t100.emulator;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.NodeExecutionException;
import hu.progtech.cd2t100.computation.NodeMemento;

import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class Emulator {
  private Timer clockSignalTimer;

  private BlockingQueue<Integer> updateQueue;

  private long clockFrequency;

  private HashMap<String, Node> nodes;

  private ArrayList<CommunicationPort> communicationPorts;

  public Emulator(long clockFrequency) {
    this.clockFrequency = clockFrequency;

    updateQueue = new SynchronousQueue<>();

    nodes = new HashMap<>();

    communicationPorts = new ArrayList<>();
  }

  public long getClockFrequency() {
    return clockFrequency;
  }

  public BlockingQueue<Integer> getUpdateQueue() {
    return updateQueue;
  }

  public void start() {
    clockSignalTimer = new Timer();

    clockSignalTimer.scheduleAtFixedRate(
      new EmulatorTask(), 0, clockFrequency);
  }

  private class EmulatorTask extends TimerTask {
    @Override
    public void run() {
      for (CommunicationPort cp : communicationPorts) {
        cp.step();
      }

      for (Node n : nodes.values()) {
        try {
          n.step();
        } catch (NodeExecutionException e) {

        }
      }

      try {
        updateQueue.put(1);
      } catch (InterruptedException e) {

      }
    }
  }
}
