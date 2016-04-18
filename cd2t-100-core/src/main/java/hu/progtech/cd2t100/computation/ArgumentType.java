package hu.progtech.cd2t100.computation;

/**
 *  Enumeration of the possible types a value passed to an instruction can
 *  have.
 */
public enum ArgumentType {
  /**
   *  The argument is an integer number.
   */
  NUMBER,

  /**
   *  The argument is the name of a port.
   */
  PORT,

  /**
   *  The argument is the name of a register.
   */
  REGISTER,

  /**
   * The argument is a label.
   */
  LABEL,

  /**
   * Preprocessor rule arguments.
   */
  RULE_VALUE,

  /**
   *  Indicates the unevaluted state of the argument. 
   */
  NOT_EVALUATED
}
