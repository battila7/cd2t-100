package hu.progtech.cd2t100.computation.io;

abstract class Port {
  protected int[] contents;

  protected final int capacity;

  Port(int capacity) {
    contents = new int[capacity];

    this.capacity = capacity;
  }

  public abstract int[] getContents();

  public abstract boolean hasData();
}
