package hu.progtech.cd2t100.computation;

public class Argument {
  private String value;

  private ArgumentType argumentType;

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
}
