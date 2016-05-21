package hu.progtech.cd2t100.formal;

/**
 *  This kind of exception occurs when the formal parameter list of an
 *  {@code apply} method does not meet the requirements. The requirements
 *  are the following:
 *  <ul>
 *    <li>the first parameter is of type {@code ExecutionEnvironment},</li>
 *    <li>every other parameter is annotated with a valid {@code Parameter}
 *        annotation,</li>
 *    <li>the annotation's {@code parameterType} value must match the
 *        type of the annotated formal parameter,</li>
 *    <li>a parameter with an implicit value must not be followed by a parameter
 *        without an implicit value.</li>
 *  </ul>
 */
public class InvalidFormalParameterListException extends Exception {

  /**
   *  Constructs a new {@code InvalidFormalParameterListException} with
   *  the specified message.
   *
   *  @param message the message of the exception
   */
  public InvalidFormalParameterListException(String message) {
    super(message);
  }
}
