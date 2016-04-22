package hu.progtech.cd2t100.computation.io;

/**
 *  Represents a port that can store an arbitrary number of integer numbers.
 *  Note that even {@code Register}s are ports too, but they are distinguished
 *  from other ports because they work in a very different way.
 */
abstract class Port {
  protected int[] contents;

  protected final int capacity;

  /**
   *  Constructs a new {@code Port} with the specified capacity.
   *
   *  @param capacity the capacity; must be larger than 0
   */
  Port(int capacity) {
    this.capacity = capacity;
  }

  public abstract int[] getContents();

  public abstract boolean hasData();

  public abstract void reset();

  /**
   *  Gets the capacity.
   *
   *  @return the capacity
   */
  public int getCapacity() {
    return capacity;
  }
}
