package hu.progtech.cd2t100.asm;

/**
 * Represents a code element (instruction, argument, etc.) in the source code.
 */
abstract class CodeElement {
  protected final Location location;

  /**
   * Consturcts a new code element with the specified location.
   *
   * @param location The location the {@code CodeElement} is at.
   */
  public CodeElement(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }
}
