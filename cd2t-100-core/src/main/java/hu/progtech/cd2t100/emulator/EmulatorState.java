package hu.progtech.cd2t100.emulator;

public enum EmulatorState {
  STOPPED() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      if (changeRequest == StateChangeRequest.ERROR) {
        emulator.setState(ERROR);
      } else {
        emulator.setState(READY);
      }
    }
  },

  ERROR() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      emulator.setState(STOPPED);
    }
  },

  READY() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      emulator.setState(RUNNING);
    }
  },

  RUNNING() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      emulator.setState(RUNNING);
    }
  };

  /* package */ abstract void onRequest(Emulator emulator, StateChangeRequest changeRequest);
}
