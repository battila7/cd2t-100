package hu.progtech.cd2t100.asm;

import hu.progtech.cd2t100.computation.Argument;

public class InstructionElement {
  private final int lineNumber;

  private final String opcode;

  private final Argument[] arguments;

  public InstructionElement(int lineNumber, String opcode,
                            Argument[] arguments)
  {
    this.lineNumber = lineNumber;
    this.opcode = opcode;
    this.arguments = arguments;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public String getOpcode() {
    return opcode;
  }

  public Argument[] getArguments() {
    return arguments;
  }

  @Override
  public String toString() {
    String result = opcode;

    result += " l:" + lineNumber + ", ";

    int argsNum = arguments.length;

    for (int i = 0; i < argsNum; ++i) {
      result += arguments[i].toString();

      if (i < argsNum - 1) {
        result += ", ";
      }
    }

    return result;
  }
}
