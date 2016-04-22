package hu.progtech.cd2t100.computation;

import java.util.HashMap;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

/**
 *  A builder class that makes easier the instantiation of
 *  {@code Node} objects.
 */
public class NodeBuilder {
  private String globalName;

  private int maximumSourceCodeLines;

  private InstructionRegistry instructionRegistry;

  private HashMap<String, Register> registerMap;
  private HashMap<String, CommunicationPort> readablePortMap;
  private HashMap<String, CommunicationPort> writeablePortMap;

  /**
   *  Constructs a new {@code NodeBuilder}.
   */
  public NodeBuilder() {
    registerMap = new HashMap<>();
    readablePortMap = new HashMap<>();
    writeablePortMap = new HashMap<>();
  }

  /**
   *  Sets the global name of the {@code Node} being built.
   *
   *  @param globalName the global name
   *
   *  @return the {@code NodeBuilder} instance
   */
  public NodeBuilder setGlobalName(String globalName) {
    this.globalName = globalName;

    return this;
  }

  /**
   *  Sets the maximum number of source code lines of the {@code Node}
   *  being built.
   *
   *  @param max the maximum number of source code lines
   *
   *  @return the {@code NodeBuilder} instance
   */
  public NodeBuilder setMaximumSourceCodeLines(int max) {
    this.maximumSourceCodeLines = max;

    return this;
  }

  /**
   *  Adds a {@code Register} to the {@code Node}.
   *
   *  @param register the register to add
   *
   *  @return the {@code NodeBuilder} instance
   */
  public NodeBuilder addRegister(Register register) {
    registerMap.put(register.getName(), register);

    return this;
  }

  /**
   *  Adds a readable {@code CommunicationPort} to the {@code Node}.
   *
   *  @param name the node-local name of port
   *  @param port the port to add
   *
   *  @return the {@code NodeBuilder} instance
   */
  public NodeBuilder addReadablePort(String name,
                                     CommunicationPort port) {
    readablePortMap.put(name, port);

    return this;
  }

  /**
   *  Adds a writeable {@code CommunicationPort} to the {@code Node}.
   *
   *  @param name the node-local name of port
   *  @param port the port to add
   *
   *  @return the {@code NodeBuilder} instance
   */
  public NodeBuilder addWriteablePort(String name,
                                      CommunicationPort port) {
    writeablePortMap.put(name, port);

    return this;
  }

  /**
   *  Sets the instruction registry of the {@code Node}.
   *
   *  @param instructionRegistry the instruction registry
   *
   *  @return the {@code NodeBuilder} instance
   */
  public NodeBuilder setInstructionRegistry(
    InstructionRegistry instructionRegistry) {
    this.instructionRegistry = instructionRegistry;

    return this;
  }

  /**
   *  Instantiates a new {@code Node} object.
   *
   *  @return the new {@code Node} instance
   */
  public Node build() {
    Node n = new Node(instructionRegistry,
                      maximumSourceCodeLines,
                      globalName,
                      registerMap,
                      readablePortMap,
                      writeablePortMap);

    for (CommunicationPort port : writeablePortMap.values()) {
      port.setSourceNode(n);
    }

    return n;
  }
}
