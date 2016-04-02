package hu.progtech.cd2t100.computation;

import java.util.List;
import java.util.ArrayList;

public class InstructionInfo {
  private Class<Instruction> instructionClass;

  private List<ArgumentType[]> callSyntaxes;

  private boolean canUseBackupRegister;

  private List<String> preprocessorRules;

  public InstructionInfo(Class<Instruction> instructionClass) {
    this.instructionClass = instructionClass;

    this.callSyntaxes = new ArrayList<>();
  }

  public Class<Instruction> getInstructionClass() {
    return instructionClass;
  }

  public List<ArgumentType[]> getCallSyntaxes() {
    return callSyntaxes;
  }
}
