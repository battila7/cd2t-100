package hu.progtech.cd2t100.asm;

import hu.progtech.cd2t100.computation.ArgumentType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents an argument of an instruction in the {@code asm} source code.
 * Stores the value and the type of the argument.
 */
public final class ArgumentElement extends CodeElement {
  private final String value;

  private final ArgumentType argumentType;

  /**
   * Constructs a new {@code ArgumentElement} at the given location with
   * the specified value and type.
   *
   * @param location The position at the argument can be found in the source code.
   * @param value The value of the argument.
   * @param argumentType The type of the argument.
   */
  public ArgumentElement(Location location,
                         String value, ArgumentType argumentType) {
    super(location);

    this.value = value;

    this.argumentType = argumentType;
  }

  /**
   * Constructs a new {@code ArgumentElement} at the given location with
   * the specified value and the default type which represents an
   * undetermined state.
   *
   * @param location The position at the argument can be found in the source code.
   * @param value The value of the argument.
   */
  public ArgumentElement(Location location, String value) {
    super(location);

    this.value = value;

    this.argumentType = ArgumentType.NOT_EVALUATED;
  }

  public String getValue() {
    return value;
  }

  public ArgumentType getArgumentType() {
    return argumentType;
  }

  /**
   * Construct the {@code String} representation from the value
   * and the type.
   *
   * @return The {@code String} representation of the argument.
   */
  @Override
  public String toString() {
    return argumentType.toString() + " - " + value
           + " @ " + location.toString();
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof ArgumentElement)) {
      return false;
    }

    ArgumentElement arg = (ArgumentElement)o;

    return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(arg.value, value)
            .append(arg.argumentType, argumentType)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(13, 33)
            .appendSuper(super.hashCode())
            .append(value)
            .append(argumentType)
            .toHashCode();
  }
}
