package hu.progtech.cd2t100.computation;

public class InstructionInfo {
  private Class<Instruction> instructionClass;

  public InstructionInfo(Class<Instruction> instructionClass) {
    this.instructionClass = instructionClass;
  }

  public Class<Instruction> getInstructionClass() {
    return instructionClass;
  }
}
