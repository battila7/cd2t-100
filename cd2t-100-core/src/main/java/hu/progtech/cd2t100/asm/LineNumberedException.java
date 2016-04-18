package hu.progtech.cd2t100.asm;

/**
 * This exception type stores the location of the erroneus code fragment.
 */
public abstract class LineNumberedException extends Exception {
  protected final Location location;

  /**
   *  Constructs a new {@code LineNumberedException} with the given location.
   *
   *  @param location the location of the erroneus code fragment
   */
  public LineNumberedException(Location location) {
    this.location = location;
  }

  /**
   *  Returns the location of the code causing this exception.
   *
   *  @return the location
   */
  public Location getLocation() {
    return location;
  }

  @Override
  public String getMessage() {
    return "At line " + location.getLine() + ", character " + location.getCharPositionInLine() + ": ";
  }
}
