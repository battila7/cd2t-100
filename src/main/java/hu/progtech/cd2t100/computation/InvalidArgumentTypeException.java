package hu.progtech.cd2t100.computation;

public final class InvalidArgumentTypeException extends Exception {
  private final String opcode;

  private final int index;

  private final ArgumentType receivedArgumentType;

  private final ArgumentType expectedArgumentType;

  public InvalidArgumentTypeException(
    String opcode,
    int index,
    ArgumentType receivedArgumentType,
    ArgumentType expectedArgumentType
  ) {
    this.opcode = opcode;
    this.index = index;
    this.receivedArgumentType = receivedArgumentType;
    this.expectedArgumentType = expectedArgumentType;
  }

  public String getOpcode() {
    return opcode;
  }

  public int getIndex() {
    return index;
  }

  public ArgumentType getReceivedArgumentType() {
    return receivedArgumentType;
  }

  public ArgumentType getExpectedArgumentType() {
    return expectedArgumentType;
  }
}
