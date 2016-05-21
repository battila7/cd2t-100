package hu.progtech.cd2t100.game.cli;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

/**
 *  {@code Updater} is intended to run in its own thread and receive the
 *  data emitted by an {@code Emulator} instance. {@code Updater} does not
 *  listen for state changes but cycle data. Upon receiving new data {@code Updater}
 *  prints it to the console. The {@code Updater} observes the
 *  actual output port contents and checks if they match the expected port
 *  contents.
 */
class Updater implements Runnable {
  private final Emulator emulator;

  private final BlockingQueue<EmulatorCycleData> cycleDataQueue;

  private final Map<String, List<Integer>> expectedPortContents;

  private final Map<String, List<Integer>> outputPortContents;

  /**
   *  Constructs a new {@code Updater} for the specified {@code Emulator}.
   *  The newly constructed {@code Updater} will check in every emulator cycle
   *  if the port contents match the expected port contents.
   *
   *  @param emulator the watched {code Emulator}
   *  @param outputPortContents Map containing the acutal output port contents.
   *                            Initially only contains the port names as keys.
   *  @param expectedPortContents the expected contents of the output ports
   */
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
