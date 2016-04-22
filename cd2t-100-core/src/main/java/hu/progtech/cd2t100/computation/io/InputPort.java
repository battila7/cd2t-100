package hu.progtech.cd2t100.computation.io;

import java.util.Arrays;

/**
 *  {@code InputPort} can store an arbitrary number of values and
 *  publish one at a time per emulator cycle. {@code InputPort} is not
 *  writeable.
 *
 *  It uses an internal data pointer to determine which value to publish next.
 */
public class InputPort extends CommunicationPort {
  private int dataPointer;

  private boolean readPerformed;

  /**
   *  Constructs a new {@code InputPort} with the specified global name
   *  storing the passed contents. The passed array gets copied.
   *
   *  @param globalName the global name
   *  @param contents the contents
   */
  public InputPort(String globalName, int[] contents) {
    super(globalName, contents.length);

    this.contents = Arrays.copyOf(contents, contents.length);

    reset();
  }

  /**
   *  Gets the internal data pointer. {@code -1} before the first emulator cycle.
   *
   *  @return the data pointer
   */
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

  /**
   *  Sets the data pointer to its default state ({@code -1}).
   */
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

  /**
   *  Does nothing, the {@code InputPort} cannot be written.
   *
   *  @param data ignored
   */
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
