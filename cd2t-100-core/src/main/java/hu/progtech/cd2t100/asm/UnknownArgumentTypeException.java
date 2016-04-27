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

  /**
   *  Constructs a new {@code UnknownArgumentTypeException} with the given location
   *  and argument value.
   *
   *  @param location the location of the argument's occurrence
   *  @param argValue the value of the offending argument
   */
  public UnknownArgumentTypeException(Location location,  String argValue) {
    super(location);

    this.argValue = argValue;
  }

  /**
   *  Returns the argument's value.
   *
   *  @return the argument's value
   */
  public String getArgValue() {
    return argValue;
  }

  @Override
  public String getMessage() {
    return "Failed to determine argument type for \"" + argValue
           + "\". Please check your spelling.";
  }
}
