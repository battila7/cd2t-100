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

  /**
   *  Gets the contents stored in the {@code Port}.
   *
   *  @return the contents
   */
  public abstract int[] getContents();

  /**
   *  Whether the port has data or empty.
   *
   *  @return {@code true} if the port has data, {@code false} otherwise
   */
  public abstract boolean hasData();

  /**
   *  Resets the {@code Port} to its defaults.
   */
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
