package hu.progtech.cd2t100.instructions.standard;

import hu.progtech.cd2t100.computation.Instruction;
import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.CallSyntax;
import hu.progtech.cd2t100.computation.Opcode;

@Opcode("NEG")
@CallSyntax({ })
public class NegInstruction extends Instruction {
  /**
   * Node-interface is not yet completed, therefore apply is
   * just a stub.
   */
  public void apply(Node node) {

  }
}
