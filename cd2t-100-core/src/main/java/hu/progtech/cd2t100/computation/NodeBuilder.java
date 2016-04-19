package hu.progtech.cd2t100.computation;

import java.util.HashMap;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class NodeBuilder {
  private int maximumSourceCodeLines;

  private InstructionRegistry instructionRegistry;

  private HashMap<String, Register> registerMap;
  private HashMap<String, CommunicationPort> readablePortMap;
  private HashMap<String, CommunicationPort> writeablePortMap;

  public NodeBuilder() {
    registerMap = new HashMap<>();
    readablePortMap = new HashMap<>();
    writeablePortMap = new HashMap<>();
  }

  public NodeBuilder setMaximumSourceCodeLines(int max) {
    this.maximumSourceCodeLines = max;

    return this;
  }

  public NodeBuilder addRegister(Register register) {
    registerMap.put(register.getName(), register);

    return this;
  }

  public NodeBuilder addReadablePort(String name,
                                     CommunicationPort port) {
    readablePortMap.put(name, port);

    return this;
  }

  public NodeBuilder addWriteablePort(String name,
                                      CommunicationPort port) {
    writeablePortMap.put(name, port);

    return this;
  }

  public NodeBuilder addInstructionRegistry(
    InstructionRegistry instructionRegistry) {
    this.instructionRegistry = instructionRegistry;

    return this;
  }

  public Node build() {
    Node n = new Node(instructionRegistry,
                      maximumSourceCodeLines,
                      registerMap,
                      readablePortMap,
                      writeablePortMap);

    for (CommunicationPort port : writeablePortMap.values()) {
      port.setSourceNode(n);
    }

    return n;
  }
}
