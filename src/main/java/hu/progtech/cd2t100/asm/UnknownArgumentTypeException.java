package hu.progtech.cd2t100.asm;

public final class UnknownArgumentTypeException extends LineNumberedException {
  private String argValue;

  public UnknownArgumentTypeException(Location location,  String argValue) {
    super(location);

    this.argValue = argValue;
  }

  public String getArgValue() {
    return argValue;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Failed to determine argument type for \"" + argValue
           + "\". Please check your spelling.";
  }
}
