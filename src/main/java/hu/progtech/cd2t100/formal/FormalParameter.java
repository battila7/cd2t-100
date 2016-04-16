package hu.progtech.cd2t100.formal;

import hu.progtech.cd2t100.formal.annotations.Parameter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *  Represents a formal parameter with its type and (implicit) value. The parameters of an
 *  {@code apply} call are represented with {@code FormalParameter} objects.
 */
public class FormalParameter {
  private final ParameterType parameterType;

  private final String implicitValue;

  /**
   *  Constructs a new {@code FormalParameter} object with the specified type
   *  and value. If there's no implicit value present {@code null} or empty
   *  string should be supplied as the second parameter.
   *
   *  @param parameterType the type of the formal parameter
   *  @param implicitValue the implicit value of the parameters
   */
  FormalParameter(ParameterType parameterType,
                  String implicitValue) {
    this.parameterType = parameterType;

    this.implicitValue = implicitValue;
  }

  /**
   *  Returns a {@code FormalParameter} object with data extracted from the
   *  specified {@code Parameter} annotation.
   *
   *  @param parameter the parameter annotation of an {@code apply} method's
   *                   formal parameter
   *
   *  @return a {@code FormalParameter} object wrapping the annotation's values
   */
  public static FormalParameter fromParameterAnnotation(Parameter parameter) {
    return new FormalParameter(parameter.parameterType(),
                               parameter.implicitValue());
  }

  /**
   *  Gets the type of the parameter.
   *
   *  @return the type of the parameter
   */
  public ParameterType getParameterType() {
    return parameterType;
  }

  /**
   *  Gets the implicit value of the formal parameter.
   *
   *  @return the implicit value of the parameter
   */
  public String getImplicitValue() {
    return implicitValue;
  }

  /**
   *  Whether the parameter has an implicit value or not. If the
   *  implicit value is {@code null} or the empty string then returns
   *  {@code false}.
   *
   *  @return {@code true} if the parameter has an implicit parameter, {@code false}
   *          otherwise
   */
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
