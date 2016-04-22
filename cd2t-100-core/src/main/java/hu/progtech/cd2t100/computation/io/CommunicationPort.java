package hu.progtech.cd2t100.computation.io;

import java.util.Arrays;

import hu.progtech.cd2t100.computation.Node;

/**
 *  A communication port that can be used for internode data exchange.
 *  Although it extends the {@code Port} class, its capacity is limited to
 *  one.
 *
 *  The data written to a {@code CommunicationPort} is only reachable after
 *  the port's {@code step()} method has been called.
 */
public class CommunicationPort extends Port {
  private static final int COM_PORT_CAPACITY = 1;

  protected final String globalName;

  protected boolean containsData;

  protected Node sourceNode;

  public CommunicationPort(String globalName) {
    super(COM_PORT_CAPACITY);

    contents = null;

    this.containsData = false;

    this.globalName = globalName;
  }

  public CommunicationPort(String globalName, int capacity) {
    super(capacity);

    contents = null;

    this.containsData = false;

    this.globalName = globalName;
  }

  public void setSourceNode(Node sourceNode) {
    this.sourceNode = sourceNode;
  }

  public boolean hasData() {
    return containsData;
  }

  public int[] getContents() {
    if (contents != null) {
      int[] ret = Arrays.copyOf(contents, capacity);

      return ret;
    } else {
      return null;
    }
  }

  public void reset() {
    containsData = false;

    contents = null;
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
    contents = new int[COM_PORT_CAPACITY];

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

  public String getGlobalName() {
    return globalName;
  }
}
