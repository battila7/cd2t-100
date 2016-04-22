package hu.progtech.cd2t100.emulator;

import java.util.Map;
import java.util.HashMap;

import hu.progtech.cd2t100.computation.NodeMemento;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

/**
 *  Holds the data produced during an emulator cycle. This includes {@code NodeMemento}
 *  objects of the {@code Nodes} and port values. Once created, this object holds no
 *  references to any of the originator classes, so can be safely used in a different
 *  thread.
 */
public class EmulatorCycleData {
  private final HashMap<String, Integer> portValues;

  private final HashMap<String, NodeMemento> nodeMementos;

  /**
   *  Constructs a new empty {@code EmulatorCycleData} object.
   */
  public EmulatorCycleData() {
    portValues = new HashMap<>();

    nodeMementos = new HashMap<>();
  }

  /**
   *  Adds the specified memento object.
   *
   *  @param nodeMemento the memento to add
   */
  public void addNodeMemento(NodeMemento nodeMemento) {
    nodeMementos.put(nodeMemento.getGlobalName(), nodeMemento);
  }

  /**
   *  Adds the specified port's value.
   *
   *  @param communicationPort the communication port
   */
  public void addPortValue(CommunicationPort communicationPort) {
    int[] value = communicationPort.getContents();

    if (value == null) {
      portValues.put(communicationPort.getGlobalName(), null);
    } else {
      portValues.put(communicationPort.getGlobalName(), value[0]);
    }
  }

  /**
   *  Gets the port values.
   *
   *  @return the port values
   */
  public Map<String, Integer> getPortValues() {
    return portValues;
  }

  /**
   *  Gets the node mementos.
   *
   *  @return the node mementos
   */
  public Map<String, NodeMemento> getNodeMementos() {
    return nodeMementos;
  }

  @Override
  public String toString() {
    String rep = "COMMPORTS:\n\t";

    rep += portValues.toString() + "\n";

    for (NodeMemento memento : nodeMementos.values()) {
      rep += memento.toString() + "\n";
    }

    return rep;
  }
}
