package hu.progtech.cd2t100.emulator;

import java.util.Timer;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;

public class Emulator {
  private Timer clockSignalTimer;

  private BlockingQueue<Integer> updateQueue;

  private long clockFrequency;

  public Emulator(long clockFrequency) {
    this.clockFrequency = clockFrequency;

    updateQueue = new SynchronousQueue<>();
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
      new EmulatorTask(updateQueue), 0, clockFrequency);
  }
}
