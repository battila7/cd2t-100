package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.asm.Location;
import hu.progtech.cd2t100.asm.LineNumberedException;

public final class UnknownOpcodeException extends LineNumberedException {
  private String opcode;

  public UnknownOpcodeException(Location location, String opcode) {
    super(location);

    this.opcode = opcode;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Unknown opcode: \"" + opcode + "\".";
  }
}
