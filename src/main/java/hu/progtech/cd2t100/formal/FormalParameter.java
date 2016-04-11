package hu.progtech.cd2t100.formal;

import hu.progtech.cd2t100.formal.annotations.Parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof FormalParameter)) {
      return false;
    }

    FormalParameter fp = (FormalParameter)o;

    return new EqualsBuilder()
            .append(fp.parameterType, parameterType)
            .append(fp.implicitValue, implicitValue)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(11, 47)
            .append(implicitValue)
            .append(parameterType)
            .toHashCode();
  }
}
