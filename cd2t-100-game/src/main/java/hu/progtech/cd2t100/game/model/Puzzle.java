package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

public class Puzzle {
  private String name;

  private String task;

  private List<NodeDescriptor> nodeDescriptors;

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

  @XmlElement(name = "nodes")
  public List<NodeDescriptor> getNodeDescriptors() {
    return nodeDescriptors;
  }

  public void setNodeDescriptors(List<NodeDescriptor> lst) {
    nodeDescriptors = lst;
  }

  @XmlElement(name = "communicationPort")
  public List<CommunicationPortDescriptor> getCommunicationPortDescriptors() {
    return communicationPortDescriptors;
  }

  public void setCommunicationPortDescriptors(List<CommunicationPortDescriptor> lst) {
    communicationPortDescriptors = lst;
  }

  @XmlElement(name = "inputPort")
  public List<InputPortDescriptor> getInputPortDescriptors() {
    return inputPortDescriptors;
  }

  public void setInputPortDescriptors(List<InputPortDescriptor> lst) {
    inputPortDescriptors = lst;
  }

  @XmlElement(name = "outputPort")
  public List<OutputPortDescriptor> getOutputPortDescriptors() {
    return outputPortDescriptors;
  }

  public void setOutputPortDescriptors(List<OutputPortDescriptor> lst) {
    outputPortDescriptors = lst;
  }
}
