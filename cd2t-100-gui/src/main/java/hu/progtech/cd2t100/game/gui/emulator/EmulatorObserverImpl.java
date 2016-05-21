package hu.progtech.cd2t100.game.gui.emulator;

import java.util.List;
import java.util.ArrayList;
import static java.util.stream.Collectors.toMap;
import java.util.Map;
import java.util.function.Consumer;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorState;
import hu.progtech.cd2t100.emulator.EmulatorObserver;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;

/**
 *  An implementation of the {@code EmulatorObserver} interface for the GUI
 *  application. A {@code Consumer} can be passed to this calss
 *  to consume cycle data emitted by the {@code Emulator}.
 */
public class EmulatorObserverImpl implements EmulatorObserver {
  private Emulator emulator;

  private Thread updaterThread;

  private final Map<String, List<Integer>> expectedPortContents;

  private final Map<String, List<Integer>> outputPortContents;

  private final Consumer<EmulatorCycleData> cycleDataConsumer;

  private final ReadOnlyObjectWrapper<EmulatorState> emulatorState;

  /**
   * Constructs a new {@code EmulatorObserverImpl} using the specified
   * expected output port contents and cycle data consumer. The {@code Consumer}
   * is called whenever cycle data is available.
   *
   *  @param expectedPortContents the map with the expected contents of output ports
   *  @param cycleDataConsumer a {@code Consumer} to process cycle data
   */
  public EmulatorObserverImpl(Map<String, List<Integer>> expectedPortContents,
                              Consumer<EmulatorCycleData> cycleDataConsumer)
  {
    this.outputPortContents =
      expectedPortContents.entrySet()
                          .stream()
                          .collect(toMap(Map.Entry::getKey, e -> new ArrayList<Integer>()));

    this.expectedPortContents = expectedPortContents;

    this.cycleDataConsumer = cycleDataConsumer;

    this.emulatorState = new ReadOnlyObjectWrapper<>();
  }

  /**
   *  Contains the actual state of the {@code Emulator} observed by this
   *  instance.
   *
   *  @return the property
   */
  public ReadOnlyObjectProperty<EmulatorState> emulatorStateProperty() {
    return emulatorState.getReadOnlyProperty();
  }

  /**
   *  Processes and handles a state change.
   *
   *  @param newState the new state of the {@code Emulator}
   */
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
    }
  }

  @Override
  public void setEmulator(Emulator emulator) {
    this.emulator = emulator;
  }

  /**
   *  Initializes the property holding the state of the {@code Emulator}.
   */
  public void initStateProperty() {
      emulatorState.set(emulator.getState());
  }
}
