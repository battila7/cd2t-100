package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.asm.Location;
import hu.progtech.cd2t100.asm.LineNumberedException;

/**
 *  An exception of this type os thrown when a rule demanded by an instruction
 *  has not been set.
 */
public final class PreprocessorRuleUnsetException extends LineNumberedException {
  private String opcode;

  private String ruleName;

  /**
   *  Constructs a new {@code PreprocessorRuleUnsetException} instance with the
   *  specified location, opcode and the name of the missing rule.
   *
   *  @param location the location of the exception's occurrence
   *  @param opcode the opcode of the instruction demanding the rule
   *  @param ruleName the name of the missing preprocessor rule
   */
  public PreprocessorRuleUnsetException(Location location, String opcode,
                                        String ruleName) {
    super(location);

    this.opcode = opcode;

    this.ruleName = ruleName;
  }

  @Override
  public String getMessage() {
    return "Preprocessor rule \"" + ruleName
           + "\" is demanded by \"" + opcode + "\".";
  }
}
