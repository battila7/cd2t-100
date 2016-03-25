package hu.progtech.cd2t100.instructions.standard;

import hu.progtech.cd2t100.computation.Instruction;
import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.CallSyntax;
import hu.progtech.cd2t100.computation.Opcode;
import hu.progtech.cd2t100.computation.ArgumentType;

@Opcode("NEG")
@CallSyntax({ })
public class NegInstruction extends Instruction {
  public void apply(Node node) {

  }
}
