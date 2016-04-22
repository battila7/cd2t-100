package hu.progtech.cd2t100.emulator;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

/**
 *  Builder class for easing the instantiation of {@code Emulator} objects.
 *  Using this class, an {@code Emulator} can be built step by step.
 */
public class EmulatorBuilder {
  private final Map<String, Node> nodes;
  private final List<CommunicationPort> communicationPorts;

  private long clockFrequency;

  private EmulatorObserver emulatorObserver;

  /**
   *  Constructs a new empty {@code EmulatorBuilder}.
   */
  public EmulatorBuilder() {
    nodes = new HashMap<>();

    communicationPorts = new ArrayList<>();
  }

  /**
   *  Adds the specified {@code Node} to the {@code Emulator} instance
   *  being built.
   *
   *  @param node the node
   *
   *  @return the {@code EmulatorBuilder} instance
   */
  public EmulatorBuilder addNode(Node node) {
    nodes.put(node.getGlobalName(), node);

    return this;
  }

  /**
   *  Adds the specified {@code CommunicationPort} to the {@code Emulator} instance
   *  being built.
   *
   *  @param port the port
   *
   *  @return the {@code EmulatorBuilder} instance
   */
  public EmulatorBuilder addCommunicationPort(CommunicationPort port) {
    communicationPorts.add(port);

    return this;
  }

  /**
   *  Sets the clock frequency of the {@code Emulator} instance
   *  being built.
   *
   *  @param clockFrequency the clock frequency
   *
   *  @return the {@code EmulatorBuilder} instance
   */
  public EmulatorBuilder setClockFrequency(long clockFrequency) {
    this.clockFrequency = clockFrequency;

    return this;
  }

  /**
   *  Sets the observer of the {@code Emulator} instance
   *  being built.
   *
   *  @param emulatorObserver the observer
   *
   *  @return the {@code EmulatorBuilder} instance
   */
  public EmulatorBuilder setObserver(EmulatorObserver emulatorObserver) {
    this.emulatorObserver = emulatorObserver;

    return this;
  }

  /**
   *  Instantiates a new {@code Emulator} object.
   *
   *  @return a new {@code Emulator} using the data added to the builder
   */
  public Emulator build() {
    return new Emulator(nodes, communicationPorts,
                        emulatorObserver, clockFrequency);
  }
}
