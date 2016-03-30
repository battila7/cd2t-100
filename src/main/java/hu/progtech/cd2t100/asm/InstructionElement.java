package hu.progtech.cd2t100.asm;

public class InstructionElement extends CodeElement {
  private final String opcode;

  private final ArgumentElement[] argumentElements;

  public InstructionElement(Location location, String opcode,
                            ArgumentElement[] argumentElements)
  {
    super(location);

    this.opcode = opcode;
    this.argumentElements = argumentElements;
  }

  public String getOpcode() {
    return opcode;
  }

  public ArgumentElement[] getArgumentElements() {
    return argumentElements;
  }

  @Override
  public String toString() {
    String result = opcode;

    result += " l:" + location.getLine() + ", ";

    int argsNum = argumentElements.length;

    for (int i = 0; i < argsNum; ++i) {
      result += argumentElements[i].toString();

      if (i < argsNum - 1) {
        result += ", ";
      }
    }

    return result;
  }
}
