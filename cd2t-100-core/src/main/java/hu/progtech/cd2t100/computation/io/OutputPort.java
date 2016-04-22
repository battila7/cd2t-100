package hu.progtech.cd2t100.computation.io;

import java.util.Arrays;

public class OutputPort extends CommunicationPort {
  public OutputPort(String globalName) {
    super(globalName);
  }

  @Override
  public boolean hasData() {
    return false;
  }

  @Override
  public int[] readContents() {
    return null;
  }

  @Override
  public void step() {
    contents = null;

    sourceNode.onPortRead(this);
  }
}
