package hu.progtech.cd2t100.asm;

/**
 * This exception type stores the location of the erroneus code fragment.
 */
public abstract class LineNumberedException extends Exception {
  protected final Location location;

  public LineNumberedException(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }

  @Override
  public String getMessage() {
    return "At line " + location.getLine() + ", character " + location.getCharPositionInLine() + ": ";
  }
}
