package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.HashMap;

import hu.progtech.cd2t100.formal.InstructionInfo;

public final class InstructionRegistry {
  private final Map<String, InstructionInfo> instructionMap;

  private final Map<String, String> ruleMap;

  public InstructionRegistry() {
    instructionMap = new HashMap<>();

    ruleMap = new HashMap<>();
  }

  public void registerInstruction(String opcode, InstructionInfo instructionInfo)
    throws Exception {
    /*if (instructionMap.putIfAbsent(opcode, instructionInfo) != null) {
      throw new Exception("Opcode is already registered by " +
                           instructionMap.get(opcode)
                                         .getInstructionClass().getName());
    }*/
  }

  public void putRules(Map<String, String> rules) {
    ruleMap.putAll(rules);
  }

  public InstructionInfo getInstructionInfoFor(String opcode) {
    return instructionMap.get(opcode);
  }
}
