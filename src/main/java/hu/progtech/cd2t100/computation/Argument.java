package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.formal.ParameterType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Argument)) {
      return false;
    }

    Argument a = (Argument)o;

    return new EqualsBuilder()
            .append(a.parameterType, parameterType)
            .append(a.value, value)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 29)
            .append(value)
            .append(parameterType)
            .toHashCode();
  }
}
