package hu.progtech.cd2t100.computation.io;

public class CommunicationPort extends Port {
  private static final int COM_PORT_CAPACITY = 1;

  private boolean containsData;

  public CommunicationPort() {
    super(COM_PORT_CAPACITY);

    this.containsData = false;
  }

  public boolean hasData() {
    return containsData;
  }

  public int[] getContents() {
    return containsData ? contents : null;
  }
}
