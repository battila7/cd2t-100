package hu.progtech.cd2t100.computation;

public final class Argument {
  private final String value;

  private final ArgumentType argumentType;

  public Argument(String value, ArgumentType argumentType) {
    this.value = value;

    this.argumentType = argumentType;
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
