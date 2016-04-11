package hu.progtech.cd2t100.formal;

import org.apache.commons.lang3.mutable.MutableInt;

public enum ParameterType {
  NUMBER (int.class),
  READ_PORT (ReadResult.class),
  WRITE_PORT (MutableInt.class),
  REGISTER (int[].class),
  LABEL (String.class);

  private final Class<?> requiredClass;

  ParameterType(Class<?> requiredClass) {
    this.requiredClass = requiredClass;
  }

  public Class<?> getRequiredClass() {
    return requiredClass;
  }
}
