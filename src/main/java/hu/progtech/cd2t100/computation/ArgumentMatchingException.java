package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.asm.Location;
import hu.progtech.cd2t100.asm.LineNumberedException;

/**
 *  Signals that the argument matching has failed. Only thrown by
 *  {@link ArgumentMatcher#match()}.
 */
public final class ArgumentMatchingException extends LineNumberedException {
  private String message;

  /**
   * Constructs a new {@code ArgumentMatchingException} with the specified
   *  location and message.
   *
   *  @param location the location of the exception's occurrence
   *  @param message the exception's message
   */
  public ArgumentMatchingException(Location location, String message) {
    super(location);

    this.message = message;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + message;
  }
}
