package hu.progtech.cd2t100.asm;

/**
 * This exception type is used when the type of an argument cannot be
 * determined. For example (assuming register named {@code EAX} does not exist):
 * <pre>
 * {@code
 *  MOV 10 EAX
 * }
 * </pre>
 */
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
