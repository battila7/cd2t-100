package hu.progtech.cd2t100.computation;

import java.util.List;
import java.util.ArrayList;

public class InstructionInfo {
  private final Class<?> instructionClass;

  private final String opcode;

  private final List<String> usedPreprocessorRules;

  public InstructionInfo(String opcode, List<String> usedRules,
                         Class<?> instructionClass) {
    this.opcode = opcode;

    this.usedPreprocessorRules = usedRules;

    this.instructionClass = instructionClass;
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
}
