package hu.progtech.cd2t100.emulator;

/**
 *  An observer that's notified when the {@code Emulator} object it's attached to
 *  changes state. An {@code Emulator} can only be observed by only one
 *  EmulatorObserver at a time.
 */
public interface EmulatorObserver {
  /**
   *  Called when the attached emulator changes state.
   *
   *  @param newState the new state of the emulator
   */
  void onStateChanged(EmulatorState newState);

  /**
   *  Sets the observed emulator.
   *
   *  @param emulator the observed emulator
   */
  void setEmulator(Emulator emulator);
}
