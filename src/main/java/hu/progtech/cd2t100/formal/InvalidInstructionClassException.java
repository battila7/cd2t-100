package hu.progtech.cd2t100.formal;

/**
 *  This kind of exception occurs when a Groovy class does not meet
 *  the requirements of the instruction classes. The requirements are the
 *  following:
 *  <ul>
 *    <li>the class must be annotated with exactly one {@code Opcode} annotation</li>
 *    <li>the class must be annotated with at most one {@code Rules} annotation</li>
 *    <li>the class must contain at least one method with the following signature:
 *        <pre>
 *          public static T apply(ExecutionEnvironment, zero or more other params)
 *        </pre>
 *        where {@code T} is not restricted, because return values are thrown
 *        away and parameters other than the first one must be of type
 *        <ul>
 *          <li>{@code int}</li>
 *          <li>{@code int[]}</li>
 *          <li>{@code MutableInt}</li>
 *          <li>{@code ReadResult}</li>
 *          <li>{@code String}</li>
 *        </ul>
 *
 *        any other methods are ignored. Only methods with the exactly same syntax as
 *        above are processed.
 *    </li>
 *  </ul>
 */
public class InvalidInstructionClassException extends Exception {
  private static final String DEFAULT_MESSAGE =
    "Opcode annotation not found or supplied value was not correct.";

  /**
   *  Constructs a new {@code InvalidInstructionClassException} with the default
   *  message.
   */
  public InvalidInstructionClassException() {
    super(DEFAULT_MESSAGE);
  }

  /**
   *  Constructs a new {@code InvalidInstructionClassException} with the specified
   *  message.
   *
   *  @param message the message of the exception
   */
  public InvalidInstructionClassException(String message) {
    super(message);
  }
}
