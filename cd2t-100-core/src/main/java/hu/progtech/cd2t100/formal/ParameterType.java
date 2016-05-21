package hu.progtech.cd2t100.formal;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 *  Indicates the type of a formal (and after argument matching, actual)
 *  parameter. Should not be confused with
 *  {@link hu.progtech.cd2t100.computation.ArgumentType}.
 */
public enum ParameterType {
  /**
   *  The formal parameter is an integer number.
   */
  NUMBER (int.class),

  /**
   *  The formal parameter is a readable port.
   */
  READ_PORT (ReadResult.class),

  /**
   * The formal parameter is a writeable port.
   */
  WRITE_PORT (MutableInt.class),

  /**
   *  The formal parameter is a register.
   */
  REGISTER (int[].class),

  /**
   *  The formal parameter is a label.
   */
  LABEL (String.class);

  private final Class<?> requiredClass;

  private ParameterType(Class<?> requiredClass) {
    this.requiredClass = requiredClass;
  }

  /**
   *  Gets the required {@code Class} of the enum value. Each enum value is
   *  mapped to a different Java class that can be used in the Groovy
   *  instruction code.
   *
   *  @return the required class
   */
  public Class<?> getRequiredClass() {
    return requiredClass;
  }
}
