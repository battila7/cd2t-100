package hu.progtech.cd2t100.asm;

import hu.progtech.cd2t100.computation.ArgumentType;

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
    return argumentType.toString() + " - " + value;
  }
}
