package hu.progtech.cd2t100.computation.io;

/**
 *  {@code Register} is a general purpose storage register that can
 *  store an arbitrary number of integers. It's a special port that can
 *  always be written or read.
 *
 *  A {@code Register} can be used to implement a stack, a queue or even
 *  some sort of a random access memory.
 */
public class Register extends Port {
  private final String name;

  /**
   *  Constructs a new {@code Register} with the specified capacity and name.
   *  Name is a node-local name, not global.
   *
   *  @param capacity the capacity
   *  @param name the node-local name
   */
  public Register(int capacity, String name) {
    super(capacity);

    contents = new int[capacity];

    this.name = name;
  }

  /**
   *  Gets the name.
   *
   *  @return the name
   */
  public String getName() {
    return name;
  }

  @Override
  public int[] getContents() {
    return contents;
  }

  @Override
  public boolean hasData() {
    return true;
  }

  @Override
  public void reset() {
    contents = new int[capacity];
  }
}
