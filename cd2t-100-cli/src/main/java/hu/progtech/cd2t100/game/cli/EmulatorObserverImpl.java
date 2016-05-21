package hu.progtech.cd2t100.game.cli;

import java.util.List;
import java.util.Map;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorState;
import hu.progtech.cd2t100.emulator.EmulatorObserver;

/**
 *  An implementation of the {@code EmulatorObserver} interface for the CLI
 *  application. Spawns a thread to inform the user about the data produced by
 *  the emulator and listens for state changes.
 */
class EmulatorObserverImpl implements EmulatorObserver {
  private Emulator emulator;

  private Thread updaterThread;

  private Map<String, List<Integer>> expectedPortContents;

  private Map<String, List<Integer>> outputPortContents;

  /**
   *  Constructs a new {@code EmulatorObserverImpl} instance with the specified
   *  output port contents and expected contents.
   *
   *  @param outputPortContents the contents of the output ports
   *  @param expectedPortContents the expected contents of the output ports
   */
  public EmulatorObserverImpl(Map<String, List<Integer>> outputPortContents,
                              Map<String, List<Integer>> expectedPortContents)
  {
    this.outputPortContents = outputPortContents;

    this.expectedPortContents = expectedPortContents;
  }

  @Override
  public void onStateChanged(EmulatorState newState) {
    if (newState == EmulatorState.RUNNING) {
      /*
       *	If in PAUSED state, we reuse the updaterThread.
       */
      if (updaterThread == null) {
        updaterThread = new Thread(new Updater(emulator,
                                               outputPortContents,
                                               expectedPortContents));

        updaterThread.start();
      }
    } else if (newState == EmulatorState.STOPPED) {
      /*
       *	If previous state was ERROR, updaterThread is null.
       */
      if (updaterThread != null) {
        updaterThread.interrupt();

        updaterThread = null;
      }

      for (List<Integer> list : outputPortContents.values()) {
        list.clear();
      }
    } else if (newState == EmulatorState.SUCCESS) {
      updaterThread.interrupt();

      updaterThread = null;
    } else if (newState == EmulatorState.ERROR) {
      System.out.println("Now in ERROR state because of the following: ");
      System.out.println(emulator.getNodeExceptionMap());
      System.out.println(emulator.getCodeExceptionMap());
    }
  }

  @Override
  public void setEmulator(Emulator emulator) {
    this.emulator = emulator;
  }
}
