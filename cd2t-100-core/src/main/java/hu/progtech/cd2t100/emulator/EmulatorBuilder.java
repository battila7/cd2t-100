package hu.progtech.cd2t100.emulator;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class EmulatorBuilder {
  private final Map<String, Node> nodes;
  private final List<CommunicationPort> communicationPorts;

  private long clockFrequency;

  private EmulatorObserver emulatorObserver;

  public EmulatorBuilder() {
    nodes = new HashMap<>();

    communicationPorts = new ArrayList<>();
  }

  public EmulatorBuilder addNode(Node node) {
    nodes.put(node.getGlobalName(), node);

    return this;
  }

  public EmulatorBuilder addCommunicationPort(CommunicationPort port) {
    communicationPorts.add(port);

    return this;
  }

  public EmulatorBuilder setClockFrequency(long clockFrequency) {
    this.clockFrequency = clockFrequency;

    return this;
  }

  public EmulatorBuilder setObserver(EmulatorObserver emulatorObserver) {
    this.emulatorObserver = emulatorObserver;

    return this;
  }

  public Emulator build() {
    return new Emulator(nodes, communicationPorts,
                        emulatorObserver, clockFrequency);
  }
}
