package hu.progtech.cd2t100.computation.io;

import java.util.Arrays;

/**
 *  A communication port that can be used for internode data exchange.
 *  Although it extends the {@code Port} class, its capacity is limited to
 *  one.
 *
 *  The data written to a {@code CommunicationPort} is only reachable after
 *  the port's {@code step()} method has been called.
 */
public class InputPort extends CommunicationPort {
  private int dataPointer;

  private boolean readPerformed;

  public InputPort(String globalName, int[] contents) {
    super(globalName, contents.length);

    this.contents = Arrays.copyOf(contents, contents.length);

    this.dataPointer = -1;

    this.readPerformed = true;
  }

  public int getDataPointer() {
    return dataPointer;
  }

  @Override
  public boolean hasData() {
    return containsData;
  }

  @Override
  public int[] getContents() {
    if (readPerformed) {
      return null;
    }
    
    int[] ret = null;

    if ((dataPointer < capacity) && (dataPointer >= 0)) {
      ret = new int[1];

      ret[0] = contents[dataPointer];
    }

    return ret;
  }

  @Override
  public void reset() {
    dataPointer = -1;

    readPerformed = true;
  }

  @Override
  public int[] readContents() {
    if (!containsData) {
      return null;
    }

    int[] ret = new int[1];

    if ((dataPointer < capacity) && (dataPointer >= 0)) {
      ret[0] = contents[dataPointer];
    } else {
      ret[0] = 0;
    }

    readPerformed = true;

    return ret;
  }

  @Override
  public void write(int data) {
    return;
  }

  @Override
  public void step() {
    if (readPerformed) {
      ++dataPointer;

      readPerformed = false;

      containsData = false;

      return;
    }

    if ((!containsData) && (dataPointer < capacity) && (dataPointer >= 0)) {
      containsData = true;
    }
  }
}
