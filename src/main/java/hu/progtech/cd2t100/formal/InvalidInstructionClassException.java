package hu.progtech.cd2t100.formal;

public class InvalidInstructionClassException extends Exception {
  private static final String DEFAULT_MESSAGE =
    "Opcode annotation not found or supplied value was not correct.";

  public InvalidInstructionClassException() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidInstructionClassException(String message) {
    super(message);
  }
}
