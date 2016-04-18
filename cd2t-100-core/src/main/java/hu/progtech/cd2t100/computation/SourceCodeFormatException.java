package hu.progtech.cd2t100.computation;

/**
 *  A {@code SourceCodeFormatException} is thrown if the source code is
 *  longer than the specified node-specific maximum length or its value is
 *  {@code null}.
 */
public class SourceCodeFormatException extends Exception {

  /**
   *  Constructs a new {@code SourceCodeFormatException} with the
   *  specified message.
   *
   *  @param message the message of the exception
   */
  public SourceCodeFormatException(String message) {
    super(message);
  }
}
