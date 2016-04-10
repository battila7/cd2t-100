package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.asm.Location;
import hu.progtech.cd2t100.asm.LineNumberedException;

public final class PreprocessorRuleUnsetException extends LineNumberedException {
  private String opcode;

  private String ruleName;

  public PreprocessorRuleUnsetException(Location location, String opcode,
                                        String ruleName) {
    super(location);

    this.opcode = opcode;

    this.ruleName = ruleName;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Preprocessor rule \"" + ruleName
           + "\" is demanded by \"" + opcode + "\".";
  }
}
