package hu.progtech.cd2t100.game.gui.emulator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

/**
 *  {@code Updater} is intended to run in its own thread and receive the
 *  data emitted by an {@code Emulator} instance. {@code Updater} does not
 *  listen for state changes but cycle data. The {@code Updater} observes the
 *  actual output port contents and checks if they match the expected port
 *  contents. Upon receiving new data {@code Updater} passes it to a specified
 *  {@code Consumer}.
 */
class Updater implements Runnable {
  private final Emulator emulator;

  private final BlockingQueue<EmulatorCycleData> cycleDataQueue;

  private final Map<String, List<Integer>> expectedPortContents;

  private final Map<String, List<Integer>> outputPortContents;

  private final Consumer<EmulatorCycleData> cycleDataConsumer;

  /**
   *  Constructs a new {@code Updater} for the specified {@code Emulator}.
   *  The newly constructed {@code Updater} will check in every emulator cycle
   *  if the port contents match the expected port contents.
   *
   *  @param emulator the observed {@code Emulator}
   *  @param outputPortContents Map containing the acutal output port contents.
   *                            Initially only contains the port names as keys.
   *  @param expectedPortContents the expected contents of the output ports
   *  @param cycleDataConsumer a consumer to process the cycle data
   */
  public Updater(Emulator emulator,
                 Map<String, List<Integer>> outputPortContents,
                 Map<String, List<Integer>> expectedPortContents,
                 Consumer<EmulatorCycleData> cycleDataConsumer) {
    this.emulator = emulator;

    this.cycleDataQueue = emulator.getCycleDataQueue();

    this.outputPortContents = outputPortContents;

    this.expectedPortContents = expectedPortContents;

    this.cycleDataConsumer = cycleDataConsumer;
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

        cycleDataConsumer.accept(ecd);

        if (success) {
          emulator.request(StateChangeRequest.SUCCESS);
        }
      }
    } catch (InterruptedException e) {
      return;
    }
  }
}
