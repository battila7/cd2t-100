package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.formal.ParameterType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *  Represents an actual argument of an {@code Instruction} object. Stores the value
 *  and the type of the argument. Not to be confused with {@code ArgumentElement} which
 *  holds location info too.
 *  @see Instruction
 */
public final class Argument {
  private final String value;

  private final ParameterType parameterType;

  /**
   *  Construct a new {@code Argument} from the given {@code value} and
   *  parameter type.
   *
   *  @param value the value of the argument
   *  @param parameterType the type of the argument's value
   */
  public Argument(String value, ParameterType parameterType) {
    this.value = value;

    this.parameterType = parameterType;
  }

  /**
   *  Returns the value of the argument.
   *
   *  @return the argument's value
   */
  public String getValue() {
    return value;
  }

  /**
   *  Returns the type of the argument.
   *
   *  @return the type of the argument
   */
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
