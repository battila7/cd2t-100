package hu.progtech.cd2t100.computation;

/**
 *  This kind of exception occurs when there was an attempt to register an
 *  {@code InstructionInfo} to a specified opcode, but the opcode
 *  has already been mapped to another info object.
 *
 *  @see hu.progtech.cd2t100.formal.InstructionInfo
 *  @see hu.progtech.cd2t100.formal.InstructionLoader
 */
public class OpcodeAlreadyRegisteredException extends Exception {

  /**
   *  Constructs a new {@code OpcodeAlreadyRegisteredException} with the
   *  specified message.
   *
   *  @param message the message contained in this exception
   */
  public OpcodeAlreadyRegisteredException(String message) {
    super(message);
  }
}
