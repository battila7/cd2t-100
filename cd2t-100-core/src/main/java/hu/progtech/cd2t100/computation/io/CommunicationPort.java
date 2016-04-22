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

  /**
   *  Constructs a new {@code CommunicationPort} with the specified global name
   *  and capacity equal to one.
   *
   *  @param globalName the global name
   */
  public CommunicationPort(String globalName) {
    super(COM_PORT_CAPACITY);

    contents = null;

    this.containsData = false;

    this.globalName = globalName;
  }

  /**
   *  Constructs a new {@code CommunicationPort} with the specified global name
   *  and capacity.
   *
   *  @param globalName the global name
   *  @param capacity the capacity
   */
  public CommunicationPort(String globalName, int capacity) {
    super(capacity);

    contents = null;

    this.containsData = false;

    this.globalName = globalName;
  }

  /**
   *  Sets the source {@code Node}. The source {@code Node} gets notified
   *  whenever a succesful read operation happens.
   *
   *  @param sourceNode the source {@code Node}
   */
  public void setSourceNode(Node sourceNode) {
    this.sourceNode = sourceNode;
  }

  /**
   *  Returns whether the port contains readable data.
   *
   *  @return whetehr the port has readable data
   */
  public boolean hasData() {
    return containsData;
  }

  /**
   *  Performs a <i>soft</i> read operation i.e. does not destroy the contents.
   *  Used for display purposes.
   *
   *  @return the contents
   */
  public int[] getContents() {
    if (contents != null) {
      int[] ret = Arrays.copyOf(contents, capacity);

      return ret;
    } else {
      return null;
    }
  }

  /**
   *  Resets the port.
   */
  public void reset() {
    containsData = false;

    contents = null;
  }

  /**
   *  Performs a <i>hard</i> read operation i.e. destroys the contents. Used by
   *  node reads.
   *
   *  @return the contents
   */
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

  /**
   *  Writes the specified data onto the port.
   *
   *  @param data the data to write
   */
  public void write(int data) {
    contents = new int[COM_PORT_CAPACITY];

    contents[0] = data;
  }

  /**
   *  Steps the port.
   */
  public void step() {
    /*
     *  Block for a cycle.
     */
    if ((contents != null) && (!containsData)) {
      containsData = true;
    }
  }

  /**
   *  Gets the global name.
   *
   *  @return the global name.
   */
  public String getGlobalName() {
    return globalName;
  }
}
