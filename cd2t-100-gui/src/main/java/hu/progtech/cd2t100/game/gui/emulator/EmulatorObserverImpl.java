package hu.progtech.cd2t100.game.gui.emulator;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorState;
import hu.progtech.cd2t100.emulator.EmulatorObserver;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;

public class EmulatorObserverImpl implements EmulatorObserver {
  private Emulator emulator;

  private Thread updaterThread;

  private Map<String, List<Integer>> expectedPortContents;

  private Map<String, List<Integer>> outputPortContents;

  private Consumer<EmulatorCycleData> cycleDataConsumer;

  private ReadOnlyObjectWrapper<EmulatorState> emulatorState;

  public EmulatorObserverImpl(Map<String, List<Integer>> outputPortContents,
                              Map<String, List<Integer>> expectedPortContents,
                              Consumer<EmulatorCycleData> cycleDataConsumer)
  {
    this.outputPortContents = outputPortContents;

    this.expectedPortContents = expectedPortContents;

    this.cycleDataConsumer = cycleDataConsumer;

    this.emulatorState = new ReadOnlyObjectWrapper<>();
  }

  public ReadOnlyObjectProperty<EmulatorState> emulatorStateProperty() {
    return emulatorState.getReadOnlyProperty();
  }

  @Override
  public void onStateChanged(EmulatorState newState) {
    emulatorState.set(emulator.getState());

    if (newState == EmulatorState.RUNNING) {
      /*
       *	If in PAUSED state, we reuse the updaterThread.
       */
      if (updaterThread == null) {
        updaterThread = new Thread(new Updater(emulator,
                                               outputPortContents,
                                               expectedPortContents,
                                               cycleDataConsumer));

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

  public void initStateProperty() {
      emulatorState.set(emulator.getState());
  }
}
