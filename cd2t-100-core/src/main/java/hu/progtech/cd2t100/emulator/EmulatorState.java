package hu.progtech.cd2t100.emulator;

/**
 *  Represents the state of an {@code Emulator} object. The following
 *  table contains the transitions from one state to another for different
 *  {@code StateChangeRequest} values:
 *
 *  <table border="1">
 *    <caption>The state transition table</caption>
 *    <tr>
 *      <td></td>
 *      <td>{@code RUN}</td>
 *      <td>{@code ERROR}</td>
 *      <td>{@code PAUSE}</td>
 *      <td>{@code STOP}</td>
 *      <td>{@code STEP}</td>
 *      <td>{@code SUCCESS}</td>
 *    </tr>
 *    <tr>
 *      <td>STOPPED</td>
 *      <td>{@code RUNNING}</td>
 *      <td>{@code ERROR}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code RUNNING}</td>
 *      <td>{@code STOPPED}</td>
 *    </tr>
 *    <tr>
 *      <td>ERROR</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code ERROR}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code STOPPED}</td>
 *    </tr>
 *    <tr>
 *      <td>RUNNING</td>
 *      <td>{@code RUNNING}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code PAUSED}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code SUCCESS}</td>
 *    </tr>
 *    <tr>
 *      <td>PAUSED</td>
 *      <td>{@code RUNNING}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code PAUSED}</td>
 *      <td>{@code STOPPED}</td>
 *      <td>{@code RUNNING}</td>
 *      <td>{@code SUCCESS}</td>
 *    </tr>
 *    <tr>
 *      <td>SUCCESS</td>
 *      <td>{@code SUCCESS}</td>
 *      <td>{@code SUCCESS}</td>
 *      <td>{@code SUCCESS}</td>
 *      <td>{@code SUCCESS}</td>
 *      <td>{@code SUCCESS}</td>
 *      <td>{@code SUCCESS}</td>
 *    </tr>
 *  </table>
 */
public enum EmulatorState {
  /**
   *  Indicates that the emulator is in stopped state. The clock generator
   *  is turned off and the source code of the nodes can be modified.
   */
  STOPPED() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      if ((changeRequest != StateChangeRequest.RUN) &&
          (changeRequest != StateChangeRequest.STEP)) {
        return;
      }

      boolean result = emulator.generateInstructions();

      if (!result) {
        emulator.setState(ERROR);

        return;
      }

      emulator.start(changeRequest == StateChangeRequest.RUN ?
                        ExecutionMode.CONTINUOUS :
                        ExecutionMode.STEPPED);

      emulator.setState(RUNNING);
    }
  },

  /**
   *  Indicates that the emulator has encountered an error. This state can be
   *  reached from {@code STOPPED} only, if instruction generation fails.
   */
  ERROR() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      if (changeRequest == StateChangeRequest.ERROR) {
        return;
      }

      emulator.stop();

      emulator.setState(STOPPED);
    }
  },

  /**
   *  The emulator is running and executing instructions. When transitioning into
   *  {@code STOPPED} state nodes and ports get reset.
   */
  RUNNING() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      if (changeRequest == StateChangeRequest.RUN) {
        return;
      }
      if (changeRequest == StateChangeRequest.PAUSE) {
        emulator.pause();

        emulator.setState(PAUSED);
      } else if (changeRequest == StateChangeRequest.SUCCESS) {
        emulator.stop();

        emulator.setState(SUCCESS);
      } else {
        emulator.stop();

        emulator.setState(STOPPED);
      }
    }
  },

  /**
   *  The emulator is paused, but can continue execution from the point it was
   *  paused at. When transitioning into {@code STOPPED} state nodes and ports get reset.
   */
  PAUSED() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      switch (changeRequest) {
        case RUN:
          emulator.start(ExecutionMode.CONTINUOUS);
          emulator.setState(RUNNING);
          break;
        case STEP:
          emulator.start(ExecutionMode.STEPPED);
          emulator.setState(RUNNING);
          break;
        case PAUSE:
          return;
        case SUCCESS:
          emulator.stop();
          emulator.setState(SUCCESS);
          break;
        default:
          emulator.stop();
          emulator.setState(STOPPED);
          break;
      }
    }
  },

  /**
   *  The emulator is stopped, but the output ports contain the same values as
   *  the expected values, which indicates a successful execution.
   */
  SUCCESS() {
    @Override
    /* package */ void onRequest(Emulator emulator, StateChangeRequest changeRequest) {
      /*
       *  Do nothing.
       */
    }
  };

  /**
   *  Handles a state change request sent to the specified {@code Emulator} instance.
   *
   *  @param emulator the subject of the state change
   *  @param changeRequest the change request signal
   */
  /* package */ abstract void onRequest(Emulator emulator, StateChangeRequest changeRequest);
}
