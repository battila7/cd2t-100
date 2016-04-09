package hu.progtech.cd2t100.formal;

import java.util.List;

public class InstructionInfo {
  private final Class<?> instructionClass;

  private final String opcode;

  private final List<String> usedPreprocessorRules;

  private final List<FormalCall> possibleCalls;

  public InstructionInfo(String opcode,
                         List<String> usedRules,
                         Class<?> instructionClass,
                         List<FormalCall> possibleCalls) {
    this.opcode = opcode;

    this.usedPreprocessorRules = usedRules;

    this.instructionClass = instructionClass;

    this.possibleCalls = possibleCalls;
  }

  public String getOpcode() {
    return opcode;
  }

  public List<String> getUsedPreprocessorRules() {
    return usedPreprocessorRules;
  }

  public Class<?> getInstructionClass() {
    return instructionClass;
  }

  public List<FormalCall> getPossibleCalls() {
    return possibleCalls;
  }

  @Override
  public String toString() {
    String str = opcode + "\n\t";

    str += usedPreprocessorRules.toString() + "\n\t";

    str += instructionClass.getName();

    for (FormalCall f : possibleCalls) {
      str += "\n\t\t" + f.toString();
    }

    return str;
  }
}
