package hu.progtech.cd2t100.asm;

abstract class CodeElement {
  protected final Location location;

  public CodeElement(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }
}
