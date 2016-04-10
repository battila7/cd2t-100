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

  public void registerInstruction(InstructionInfo info)
    throws OpcodeAlreadyRegisteredException {
    if (instructionMap.putIfAbsent(info.getOpcode(), info) != null) {
      throw new OpcodeAlreadyRegisteredException(
        "Opcode is already registered by " +
        instructionMap.get(info.getOpcode()).getInstructionClass().getName());
    }
  }

  public void putRules(Map<String, String> rules) {
    ruleMap.putAll(rules);
  }

  public InstructionInfo getInstructionInfoFor(String opcode) {
    return instructionMap.get(opcode);
  }

  public Map<String, String> getRules() {
    return ruleMap;
  }

  public String getRuleValue(String ruleName) {
    return ruleMap.get(ruleName);
  }
}
