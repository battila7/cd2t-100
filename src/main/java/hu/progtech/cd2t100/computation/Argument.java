package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.formal.ParameterType;

public final class Argument {
  private final String value;

  private final ParameterType parameterType;

  public Argument(String value, ParameterType parameterType) {
    this.value = value;

    this.parameterType = parameterType;
  }

  public String getValue() {
    return value;
  }

  public ParameterType getParameterType() {
    return parameterType;
  }

  @Override
  public String toString() {
    return parameterType.toString() + " - " + value;
  }
}
