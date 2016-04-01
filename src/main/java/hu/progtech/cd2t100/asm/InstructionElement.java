package hu.progtech.cd2t100.asm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents an instruction in the source code. Stores the opcode, the arguments
 * and the location of the instruction. During the parsing of the AST an instance
 * is created for each instruction.
 *
 * @see ArgumentElement
 */
public class InstructionElement extends CodeElement {
  private final String opcode;

  private final ArgumentElement[] argumentElements;

  /**
   * Constructs an {@code InstructionElement} with the specified location, opcode
   * and {@code argumentElement}s.
   *
   * @param location The location of the instruction in the source code.
   * @param opcode The opcode of the instruction ({@code MOV}, {@code NEG}, etc.).
   * @param argumentElements The {@code ArgumentElement} instances representing
   *                         the arguments of the instruction.
   */
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

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof InstructionElement)) {
      return false;
    }

    InstructionElement inst = (InstructionElement)o;

    return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(inst.opcode, opcode)
            //.append(inst.argumentElements, argumentElements)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 47)
            .appendSuper(super.hashCode())
            .append(opcode)
            .append(argumentElements)
            .toHashCode();
  }
}
