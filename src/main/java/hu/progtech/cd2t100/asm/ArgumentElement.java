package hu.progtech.cd2t100.asm;

import hu.progtech.cd2t100.computation.ArgumentType;

public final class ArgumentElement extends CodeElement {
  private final String value;

  private final ArgumentType argumentType;

  public ArgumentElement(Location location,
                         String value, ArgumentType argumentType) {
    super(location);

    this.value = value;

    this.argumentType = argumentType;
  }

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

  @Override
  public String toString() {
    return argumentType.toString() + " - " + value;
  }
}
