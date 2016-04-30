package hu.progtech.cd2t100.game.model;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import hu.progtech.cd2t100.game.util.TrimmingNormalizerAdapter;

/**
 *  {@code Puzzle} is a problem to be solved by the user using a
 *  CD2T-100 emulator. Along with the textual description of the problem
 *  {@code Puzzle} contains descriptors of the {@code Node}s and different
 *  ports that can be used when solving the problem.
 */
public class Puzzle {
  private String name;

  private String task;

  private ArrayList<NodeDescriptor> nodeDescriptors;

  private List<CommunicationPortDescriptor> communicationPortDescriptors;

  private List<InputPortDescriptor> inputPortDescriptors;

  private List<OutputPortDescriptor> outputPortDescriptors;

  /**
   *  Gets the name of the puzzle.
   *
   *  @return the name
   */
  public String getName() {
    return name;
  }

  /**
   *  Sets the name of the puzzle.
   *
   *  @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *  Get the textual description of the task.
   *
   *  @return the task
   */
  @XmlJavaTypeAdapter(TrimmingNormalizerAdapter.class)
  public String getTask() {
    return task;
  }

  /**
   *  Sets the task.
   *
   *  @param task the task
   */
  public void setTask(String task) {
    this.task = task;
  }

  /**
   *  Gets the list of {@code NodeDescriptor}s describing the
   *  {@code Node}s that will be instantiated when solving
   *  the {@code Puzzle}.
   *
   *  @return the list of {@code NodeDescriptor}s
   */
  @XmlElement(name="nodeDescriptor", type=NodeDescriptor.class)
  @XmlElementWrapper(name="nodes")
  public ArrayList<NodeDescriptor> getNodeDescriptors() {
    return nodeDescriptors;
  }

  /**
   *  Sets the list of {@code NodeDescriptor}s.
   *
   *  @param nodeDescriptors the list of {@code NodeDescriptor}s
   */
  public void setNodeDescriptors(ArrayList<NodeDescriptor> nodeDescriptors) {
    this.nodeDescriptors = nodeDescriptors;
  }

  /**
   *  Gets the list of {@code CommunicationPortDescriptor}s in the {@code Puzzle}.
   *
   *  @return the list of {@code CommunicationPortDescriptor}s
   */
  @XmlElement(name="communicationPortDescriptor", type=CommunicationPortDescriptor.class)
  @XmlElementWrapper(name="communicationPorts")
  public List<CommunicationPortDescriptor> getCommunicationPortDescriptors() {
    return communicationPortDescriptors;
  }

  /**
   *  Sets the list of {@code CommunicationPortDescriptor}s in the {@code Puzzle}.
   *
   *  @param list the list of {@code CommunicationPortDescriptor}s
   */
  public void setCommunicationPortDescriptors(List<CommunicationPortDescriptor> list) {
    communicationPortDescriptors = list;
  }

  /**
   *  Gets the list of {@code InputPortDescriptor}s in the {@code Puzzle}.
   *
   *  @return the list of {@code InputPortDescriptor}s
   */
  @XmlElement(name="inputPortDescriptor", type=InputPortDescriptor.class)
  @XmlElementWrapper(name="inputPorts")
  public List<InputPortDescriptor> getInputPortDescriptors() {
    return inputPortDescriptors;
  }

  /**
   *  Sets the list of {@code InputPortDescriptor}s in the {@code Puzzle}.
   *
   *  @param list the list of {@code InputPortDescriptor}s
   */
  public void setInputPortDescriptors(List<InputPortDescriptor> list) {
    inputPortDescriptors = list;
  }

  /**
   *  Gets the list of {@code OutputPortDescriptor}s in the {@code Puzzle}.
   *
   *  @return the list of {@code OutputPortDescriptor}s
   */
  @XmlElement(name="outputPortDescriptor", type=OutputPortDescriptor.class)
  @XmlElementWrapper(name="outputPorts")
  public List<OutputPortDescriptor> getOutputPortDescriptors() {
    return outputPortDescriptors;
  }

  /**
   *  Sets the list of {@code OutputPortDescriptor}s in the {@code Puzzle}.
   *
   *  @param list the list of {@code OutputPortDescriptor}s
   */
  public void setOutputPortDescriptors(List<OutputPortDescriptor> list) {
    outputPortDescriptors = list;
  }
}
