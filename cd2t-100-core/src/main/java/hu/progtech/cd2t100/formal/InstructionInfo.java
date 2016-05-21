package hu.progtech.cd2t100.formal;

import java.util.List;

/**
 *  Contains all the information needed for argument matching and
 *  instantiating {@code Instruction} objects.
 *  {@code InstructionInfo} objects are directly constructed from Groovy
 *  code.
 *
 *  @see hu.progtech.cd2t100.computation.Instruction
 */
public class InstructionInfo {
  private final Class<?> instructionClass;

  private final String opcode;

  private final List<String> usedPreprocessorRules;

  private final List<FormalCall> possibleCalls;

  /**
   *  Constructs a new {@code InstructionInfo} object
   *  from the specified parameters.
   *
   *  @param opcode the opcode of the instruction
   *  @param usedRules the rules used by the instruction
   *  @param instructionClass the {@code Class} object created from the Groovy code
   *  @param possibleCalls the list of {@code FormalCall}s representing the
   *                       {@code apply} methods of the {@code instructionClass}
   */
  public InstructionInfo(String opcode,
                         List<String> usedRules,
                         Class<?> instructionClass,
                         List<FormalCall> possibleCalls) {
    this.opcode = opcode;

    this.usedPreprocessorRules = usedRules;

    this.instructionClass = instructionClass;

    this.possibleCalls = possibleCalls;
  }

  /**
   *  Gets the opcode.
   *
   *  @return the opcode
   */
  public String getOpcode() {
    return opcode;
  }

  /**
   *  Gets the list of used preprocessor rules.
   *
   *  @return the list of used preprocessor rules
   */
  public List<String> getUsedPreprocessorRules() {
    return usedPreprocessorRules;
  }

  /**
   *  Gets the Groovy class backing this {@code InstructionInfo} instance.
   *
   *  @return the backing instruction class
   */
  public Class<?> getInstructionClass() {
    return instructionClass;
  }

  /**
   *  Gets the list of possible valid {@code FormalCall}s.
   *
   *  @return the list of possible calls
   */
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
