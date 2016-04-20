package hu.progtech.cd2t100.emulator;

public interface EmulatorObserver {
  void onStateChanged(EmulatorState newState);

  void setEmulator(Emulator emulator);
}
