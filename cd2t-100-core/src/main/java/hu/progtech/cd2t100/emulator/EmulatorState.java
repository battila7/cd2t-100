package hu.progtech.cd2t100.emulator;

public enum EmulatorState {
  STOPPED() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      StateChangeRequest req = emulator.generateInstructions();

      if (req == StateChangeRequest.ERROR) {
        emulator.setState(ERROR);
      } else {
        emulator.start((changeRequest == StateChangeRequest.STEP) ?
                                          ExecutionMode.STEPPED :
                                          ExecutionMode.CONTINUOUS);

        emulator.setState(RUNNING);
      }
    }
  },

  ERROR() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      emulator.stop();

      emulator.setState(STOPPED);
    }
  },

  RUNNING() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      if (changeRequest == StateChangeRequest.PAUSE) {
        emulator.pause();

        emulator.setState(PAUSED);
      } else {
        emulator.stop();

        emulator.setState(STOPPED);
      }
    }
  },

  PAUSED() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      if (changeRequest == StateChangeRequest.STOP) {
        emulator.stop();

        emulator.setState(STOPPED);
      } else {
        emulator.start((changeRequest == StateChangeRequest.STEP) ?
                                          ExecutionMode.STEPPED :
                                          ExecutionMode.CONTINUOUS);

        emulator.setState(RUNNING);
      }
    }
  };

  /* package */ abstract void onRequest(Emulator emulator, StateChangeRequest changeRequest);
}
