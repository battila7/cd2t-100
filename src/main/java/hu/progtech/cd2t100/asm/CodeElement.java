package hu.progtech.cd2t100.asm;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof CodeElement)) {
      return false;
    }

    CodeElement elem = (CodeElement)o;

    return elem.location.equals(location);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(11, 29)
            .append(location)
            .toHashCode();
  }
}
