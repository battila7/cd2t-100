package hu.progtech.cd2t100.computation.io;

import java.util.Arrays;

/**
 *  {@code OutputPort} acts as a sink port. It can consume any number of values
 *  but only one at a time. It does not keep track of the values written to it,
 *  this must be done by the client.
 */
public class OutputPort extends CommunicationPort {
  /**
   *  Constructs a new {@code OutputPort} with the specified global name.
   *
   *  @param globalName the global name
   */
  public OutputPort(String globalName) {
    super(globalName);
  }

  /**
   *  Always returns {@code false}.
   *
   *  @return {@code false}.
   */
  @Override
  public boolean hasData() {
    return false;
  }

  /**
   *  Always returns {@code null}.
   *
   *  @return {@code null}
   */
  @Override
  public int[] readContents() {
    return null;
  }

  @Override
  public void step() {
    if (contents != null) {
      contents = null;
    } else {
      sourceNode.onPortRead(this);
    }
  }
}
