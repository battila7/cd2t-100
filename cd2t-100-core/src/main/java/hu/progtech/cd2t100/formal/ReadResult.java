package hu.progtech.cd2t100.formal;

/**
 *  A helper class wrapping an {@code int} used to pass data from readable
 *  ports to {@code apply} methods.
 *  Since both {@code NUMBER} and {@code READ_PORT} parameter types
 *  require {@code int} in the Groovy instruction classes,
 *  a helper class must have been introduced in order to
 *  make it possible to create different overloads based on these
 *  argument types.
 */
public class ReadResult {
  private int value;

  /**
   * Constructs a new {@code ReadResult} wrapping the passed {@code int}.
   *
   *  @param value the value to be wrapped
   */
  public ReadResult(int value) {
    this.value = value;
  }

  /**
   *  Gets the value stored in the {@code ReadResult}.
   *
   *  @return the value
   */
  public int getValue() {
    return value;
  }
}
