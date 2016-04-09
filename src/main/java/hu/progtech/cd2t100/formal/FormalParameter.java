package hu.progtech.cd2t100.formal;

import hu.progtech.cd2t100.formal.annotations.Parameter;

public class FormalParameter {
  private final ParameterType parameterType;

  private final String implicitValue;

  FormalParameter(ParameterType parameterType,
                  String implicitValue) {
    this.parameterType = parameterType;

    this.implicitValue = implicitValue;
  }

  public static FormalParameter fromParameterAnnotation(Parameter parameter) {
    return new FormalParameter(parameter.parameterType(),
                               parameter.implicitValue());
  }

  public ParameterType getParameterType() {
    return parameterType;
  }

  public String getImplicitValue() {
    return implicitValue;
  }

  public boolean hasImplicitValue() {
    return !((implicitValue == null) || (implicitValue.equals("")));
  }

  @Override
  public String toString() {
    String str = parameterType.toString();

    if (hasImplicitValue()) {
      str += " I: " + implicitValue;
    }

    return str;
  }
}
