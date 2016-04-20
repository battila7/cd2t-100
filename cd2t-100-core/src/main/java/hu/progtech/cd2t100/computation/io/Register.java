package hu.progtech.cd2t100.computation.io;

public class Register extends Port {
  private final String name;

  public Register(int capacity, String name) {
    super(capacity);

    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int[] getContents() {
    return contents;
  }

  public boolean hasData() {
    return true;
  }

  public void reset() {
    contents = new int[capacity];
  }
}
