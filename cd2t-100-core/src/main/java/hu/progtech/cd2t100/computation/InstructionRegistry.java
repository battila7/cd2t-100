package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.formal.InstructionInfo;

/**
 *  A registry class containing mapping of opcodes to {@code InstructionInfo} objects
 *  and currently active preprocessor rules. It gets injected into the nodes
 *  enabling them to instantiate {@code Instruction} objects.
 *
 *  @see hu.progtech.cd2t100.formal.InstructionInfo
 *  @see hu.progtech.cd2t100.computation.Instruction
 */
public final class InstructionRegistry {
  private static final Logger	logger = LoggerFactory.getLogger(InstructionRegistry.class);

  private final Map<String, InstructionInfo> instructionMap;

  private final Map<String, String> effectiveRuleMap;

  private final Map<String, String> defaultRuleMap;

  /**
   *  Constructs a new {@code InstructionRegistry} object with the specified
   *  default rules.
   *
   *  @param defaultRules a default mapping of preprocessor rules
   */
  public InstructionRegistry(Map<String, String> defaultRules) {
    instructionMap = new HashMap<>();

    effectiveRuleMap = new HashMap<>();

    defaultRuleMap = defaultRules;
  }

  /**
   *  Registers an {@code InstructionInfo} object in the registry.
   *
   *  @param info the instruction info to be registered
   *
   *  @throws OpcodeAlreadyRegisteredException
   *           If there's an {@code InstructionInfo} object already associated
   *           with the parameter's opcode.
   */
  public void registerInstruction(InstructionInfo info)
    throws OpcodeAlreadyRegisteredException {

    logger.info("Attempt to register info for {}", info);

    if (instructionMap.putIfAbsent(info.getOpcode(), info) != null) {
      throw new OpcodeAlreadyRegisteredException(
        "Opcode is already registered by " +
        instructionMap.get(info.getOpcode()).getInstructionClass().getName());
    }
  }

  /**
   *  Resets the (effective) preprocessor map to the default map.
   */
  public void resetRules() {
    effectiveRuleMap.clear();

    effectiveRuleMap.putAll(defaultRuleMap);
  }

  /**
   *  Puts the given mapping into the (effective) rule map. Overrides any
   *  previous mappings.
   *
   *  @param rules the rules to be inserted put into the effective rule map
   */
  public void putRules(Map<String, String> rules) {
    logger.trace("The following rules have been put into the map: {}", rules);

    effectiveRuleMap.putAll(rules);
  }

  /**
   *  Gets the {@code InstructionInfo} object associated with the specified opcode.
   *
   *  @param opcode the opcode
   *
   *  @return the {@code InstructionInfo} object associated with the opcode or
   *          {@code null} if the opcode is not registered yet.
   */
  public InstructionInfo getInstructionInfoFor(String opcode) {
    return instructionMap.get(opcode);
  }

  /**
   *  Returns the effective rule map of this registry.
   *
   *  @return the effective rule map
   */
  public Map<String, String> getRules() {
    return effectiveRuleMap;
  }

  /**
   *  Gets the value associated with the specified rule name.
   *
   *  @param ruleName the rule's name
   *
   *  @return the value of the specified rule
   */
  public String getRuleValue(String ruleName) {
    return effectiveRuleMap.get(ruleName);
  }
}
