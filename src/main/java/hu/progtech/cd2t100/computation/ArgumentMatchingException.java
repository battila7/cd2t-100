package hu.progtech.cd2t100.computation;

import hu.progtech.cd2t100.asm.Location;
import hu.progtech.cd2t100.asm.LineNumberedException;

public final class ArgumentMatchingException extends LineNumberedException {
  private String message;

  public ArgumentMatchingException(Location location, String message) {
    super(location);

    this.message = message;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + message;
  }
}
