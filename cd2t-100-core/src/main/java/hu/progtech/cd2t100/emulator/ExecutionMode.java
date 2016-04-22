package hu.progtech.cd2t100.emulator;

/**
 *  Enumeration of the possible execution mode of an {@code Emulator} instance.
 */
enum ExecutionMode {
  /**
   *  Only one instruction gets executed then the {@code Emulator} changes its state
   *  to {@code PAUSED}.
   */
  STEPPED,

  /**
   *  Instructions are being executed continuously.
   */
  CONTINUOUS;
}
