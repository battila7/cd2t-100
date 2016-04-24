package hu.progtech.cd2t100.game.model;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Puzzle {
  private String name;

  private String task;

  private ArrayList<NodeDescriptor> nodeDescriptors;

  private List<CommunicationPortDescriptor> communicationPortDescriptors;

  private List<InputPortDescriptor> inputPortDescriptors;

  private List<OutputPortDescriptor> outputPortDescriptors;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  @XmlElement(name="nodeDescriptor", type=NodeDescriptor.class)
  @XmlElementWrapper(name="nodes")
  public ArrayList<NodeDescriptor> getNodeDescriptors() {
    return nodeDescriptors;
  }

  public void setNodeDescriptors(ArrayList<NodeDescriptor> nodeDescriptors) {
    this.nodeDescriptors = nodeDescriptors;
  }

  @XmlElement(name="communicationPortDescriptor", type=CommunicationPortDescriptor.class)
  @XmlElementWrapper(name="communicationPorts")
  public List<CommunicationPortDescriptor> getCommunicationPortDescriptors() {
    return communicationPortDescriptors;
  }

  public void setCommunicationPortDescriptors(List<CommunicationPortDescriptor> lst) {
    communicationPortDescriptors = lst;
  }

  @XmlElement(name="inputPortDescriptor", type=InputPortDescriptor.class)
  @XmlElementWrapper(name="inputPorts")
  public List<InputPortDescriptor> getInputPortDescriptors() {
    return inputPortDescriptors;
  }

  public void setInputPortDescriptors(List<InputPortDescriptor> lst) {
    inputPortDescriptors = lst;
  }

  @XmlElement(name="outputPortDescriptor", type=OutputPortDescriptor.class)
  @XmlElementWrapper(name="outputPorts")
  public List<OutputPortDescriptor> getOutputPortDescriptors() {
    return outputPortDescriptors;
  }

  public void setOutputPortDescriptors(List<OutputPortDescriptor> lst) {
    outputPortDescriptors = lst;
  }
}
