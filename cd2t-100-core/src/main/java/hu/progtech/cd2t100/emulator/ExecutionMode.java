package hu.progtech.cd2t100.emulator;

/**
 *  Enumeration of the possible execution modes of an {@code Emulator} instance.
 */
enum ExecutionMode {
  /**
   *  After executing an instruction the {@code Emulator} changes its state
   *  to {@code PAUSED}.
   */
  STEPPED,

  /**
   *  Instructions are being executed continuously.
   */
  CONTINUOUS;
}
