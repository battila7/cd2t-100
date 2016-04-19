package hu.progtech.cd2t100.emulator;

import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

public class EmulatorTask extends TimerTask {
  private final BlockingQueue<Integer> updateQueue;

  public EmulatorTask(BlockingQueue<Integer> updateQueue) {
    this.updateQueue = updateQueue;
  }

  @Override
  public void run() {
    try {
      updateQueue.put(1);
    } catch (Exception e) {

    }
  }
}
