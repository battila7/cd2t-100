package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public final class InstructionRegistry {
  private final Map<String, InstructionInfo> instructionMap;

  public InstructionRegistry() {
    instructionMap = new HashMap<>();
  }

  public void registerInstruction(String opcode, InstructionInfo instructionInfo)
    throws Exception {
    /*if (instructionMap.putIfAbsent(opcode, instructionInfo) != null) {
      throw new Exception("Opcode is already registered by " +
                           instructionMap.get(opcode)
                                         .getInstructionClass().getName());
    }*/
  }

  public Optional<InstructionInfo> getInstructionInfoFor(String opcode) {
    return Optional.ofNullable(instructionMap.get(opcode));
  }
}
