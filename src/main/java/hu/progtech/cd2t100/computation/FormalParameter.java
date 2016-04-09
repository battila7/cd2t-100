package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.computation.annotations.Parameter;

public class FormalParameter {
  private final ArgumentType argumentType;

  private final PortAccess portAccess;

  private final String implicitValue;

  private FormalParameter(ArgumentType argumentType,
                         PortAccess portAccess,
                         String implicitValue) {
    this.argumentType = argumentType;

    this.portAccess = portAccess;

    this.implicitValue = implicitValue;
  }

  public static FormalParameter fromParameterAnnotation(Parameter parameter) {
    return new FormalParameter(parameter.argumentType(),
                               parameter.portAccess(),
                               parameter.implicitValue());
  }

  public ArgumentType getArgumentType() {
    return argumentType;
  }

  public PortAccess getPortAccess() {
    return portAccess;
  }

  public String getImplicitValue() {
    return implicitValue;
  }
}
