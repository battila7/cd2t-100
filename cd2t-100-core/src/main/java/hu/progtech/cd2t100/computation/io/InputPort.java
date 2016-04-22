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

    reset();
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
    int[] ret = null;

    if (isDataPointerValid() && (!readPerformed)) {
      ret = new int[1];

      ret[0] = contents[dataPointer];
    }

    return ret;
  }

  @Override
  public void reset() {
    dataPointer = -1;

    readPerformed = true;

    containsData = false;
  }

  @Override
  public int[] readContents() {
    int[] ret = new int[1];

    if (isDataPointerValid() && (containsData)) {
      ret[0] = contents[dataPointer];

      readPerformed = true;
    }

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
    } else  if ((!containsData) && (isDataPointerValid())) {
      containsData = true;
    }
  }

  private boolean isDataPointerValid() {
    return (dataPointer < capacity) && (dataPointer >= 0);
  }
}
