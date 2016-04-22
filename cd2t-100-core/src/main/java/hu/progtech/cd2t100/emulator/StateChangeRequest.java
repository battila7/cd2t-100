package hu.progtech.cd2t100.emulator;

/**
 *  The different signals that can initiate the state transition of an
 *  {@code Emulator} object. Please refer to the state transition table
 *  on the documentation page of {@link EmulatorState}.
 */
public enum StateChangeRequest {
  /**
   *  Behaves like a <i>next</i> signal.
   */
  RUN,

  /**
   *  Indicates an error.
   */
  ERROR,

  /**
   *  Indicates a pause request when in {@code RUNNING} state.
   */
  PAUSE,

  /**
   *  Indicates a stop request when is {@code RUNNING} or {@code PAUSED} state.
   */
  STOP,

  /**
   *  Indicates a stepped execution mode request when in {@code STOPPED} or in
   *  {@code PAUSED} state.
   */
  STEP;
}
