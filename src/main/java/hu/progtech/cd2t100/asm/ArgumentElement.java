package hu.progtech.cd2t100.asm;

import hu.progtech.cd2t100.computation.ArgumentType;

public final class ArgumentElement extends LineNumberedElement {
  private final String value;

  private final ArgumentType argumentType;

  public ArgumentElement(int lineNumber, int columnNumber,
                         String value, ArgumentType argumentType) {
    super(lineNumber, columnNumber);

    this.value = value;

    this.argumentType = argumentType;
  }

  public ArgumentElement(int lineNumber, int columnNumber, String value) {
    super(lineNumber, columnNumber);

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
