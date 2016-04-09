package hu.progtech.cd2t100.computation;

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
}
