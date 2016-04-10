package hu.progtech.cd2t100.computation;

import java.util.HashMap;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class NodeBuilder {
  private int maximumSourceCodeLines;

  private InstructionRegistry instructionRegistry;

  private HashMap<String, Register> registerMap;
  private HashMap<String, CommunicationPort> portMap;

  public NodeBuilder() {
    registerMap = new HashMap();
    portMap = new HashMap();
  }

  public NodeBuilder setMaximumSourceCodeLines(int max) {
    this.maximumSourceCodeLines = max;

    return this;
  }

  public NodeBuilder addRegister(Register register) {
    registerMap.put(register.getName(), register);

    return this;
  }

  public NodeBuilder addCommunicationPort(String name,
                                          CommunicationPort port) {
    portMap.put(name, port);

    return this;
  }

  public NodeBuilder addInstructionRegistry(
    InstructionRegistry instructionRegistry) {
    this.instructionRegistry = instructionRegistry;

    return this;
  }

  public Node build() {
    return new Node(instructionRegistry,
                    maximumSourceCodeLines,
                    registerMap,
                    portMap);
  }
}
