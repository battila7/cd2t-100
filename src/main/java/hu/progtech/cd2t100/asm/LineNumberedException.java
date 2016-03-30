package hu.progtech.cd2t100.asm;

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
