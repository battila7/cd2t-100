package hu.progtech.cd2t100.emulator;

import java.util.Map;
import java.util.HashMap;

import hu.progtech.cd2t100.computation.NodeMemento;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class EmulatorCycleData {
  private final HashMap<String, Integer> portValues;

  private final HashMap<String, NodeMemento> nodeMementos;

  public EmulatorCycleData() {
    portValues = new HashMap<>();

    nodeMementos = new HashMap<>();
  }

  public void addNodeMemento(NodeMemento nodeMemento) {
    nodeMementos.put(nodeMemento.getGlobalName(), nodeMemento);
  }

  public void addPortValue(CommunicationPort communicationPort) {
    int[] value = communicationPort.getContents();

    if (value == null) {
      portValues.put(communicationPort.getGlobalName(), null);
    } else {
      portValues.put(communicationPort.getGlobalName(), value[0]);
    }
  }

  public Map<String, Integer> getPortValues() {
    return portValues;
  }

  public Map<String, NodeMemento> getNodeMementos() {
    return nodeMementos;
  }
}
