package hu.progtech.cd2t100.game.cli;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

class Updater implements Runnable {
  private Emulator emulator;

  private BlockingQueue<EmulatorCycleData> cycleDataQueue;

  private Map<String, List<Integer>> expectedPortContents;

  private Map<String, List<Integer>> outputPortContents;

  public Updater(Emulator emulator,
                 Map<String, List<Integer>> outputPortContents,
                 Map<String, List<Integer>> expectedPortContents) {
    this.emulator = emulator;

    this.cycleDataQueue = emulator.getCycleDataQueue();

    this.outputPortContents = outputPortContents;

    this.expectedPortContents = expectedPortContents;
  }

  @Override
  public void run() {
    try {
      while (true) {
        EmulatorCycleData ecd = cycleDataQueue.take();

        Map<String, Integer> portValues = ecd.getPortValues();

        boolean success = true;

        for (Map.Entry<String, List<Integer>> entry :
              outputPortContents.entrySet())
        {
          Optional.ofNullable(portValues.get(entry.getKey()))
                  .ifPresent(x -> entry.getValue().add(x));

          List<Integer> actual = outputPortContents.get(entry.getKey()),
                        expected = expectedPortContents.get(entry.getKey());

          if (!actual.equals(expected)) {
            success = false;
          }
        }

        System.out.println(ecd);

        if (success) {
          emulator.request(StateChangeRequest.SUCCESS);
        }
      }
    } catch (InterruptedException e) {
      return;
    }
  }
}
