package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.asm.Location;
import hu.progtech.cd2t100.asm.LineNumberedException;

/**
 *  An {@code UnknownOpcodeException} occurs if there's no {@code InstructionInfo}
 *  object mapped to an opcode in the {@code InstructionRegistry}.
 */
public final class UnknownOpcodeException extends LineNumberedException {
  private String opcode;

  /**
   *  Constructs a new {@code UnknownOpcodeException} with the specified
   *  location and the unknown opcode.
   *
   *  @param location the location of the exception's occurrence
   *  @param opcode the unregistered opcode
   */
  public UnknownOpcodeException(Location location, String opcode) {
    super(location);

    this.opcode = opcode;
  }

  @Override
  public String getMessage() {
    return "Unknown opcode: \"" + opcode + "\".";
  }
}
