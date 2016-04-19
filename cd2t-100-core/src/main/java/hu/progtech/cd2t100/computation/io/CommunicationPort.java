package hu.progtech.cd2t100.computation.io;

import java.util.Arrays;

import hu.progtech.cd2t100.computation.Node;

public class CommunicationPort extends Port {
  private static final int COM_PORT_CAPACITY = 1;

  private boolean containsData;

  private Node sourceNode;

  public CommunicationPort() {
    super(COM_PORT_CAPACITY);

    contents = null;

    this.containsData = false;
  }

  public void setSourceNode(Node sourceNode) {
    this.sourceNode = sourceNode;
  }

  public boolean hasData() {
    return containsData;
  }

  public int[] getContents() {
    int[] ret = Arrays.copyOf(contents, capacity);

    return ret;
  }

  public int[] readContents() {
    if (!containsData) {
      return null;
    }

    int[] ret = Arrays.copyOf(contents, capacity);

    contents = null;

    containsData = false;

    sourceNode.onPortRead(this);

    return ret;
  }

  public void write(int data) {
    contents = new int[1];

    contents[0] = data;
  }

  public void step() {
    /*
     *  Block for a cycle.
     */
    if ((contents != null) && (!containsData)) {
      containsData = true;
    }
  }
}
