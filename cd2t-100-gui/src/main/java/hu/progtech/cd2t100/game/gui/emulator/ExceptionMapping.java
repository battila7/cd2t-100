package hu.progtech.cd2t100.game.gui.emulator;

import hu.progtech.cd2t100.asm.LineNumberedException;
import hu.progtech.cd2t100.asm.Location;

public class ExceptionMapping {
  private final String node;

  private final Location location;

  private final String message;

  public ExceptionMapping(String node, Exception exception) {
    this.node = node;

    this.message = exception.getMessage();

    this.location = new Location(0, 0);
  }

  public ExceptionMapping(String node, LineNumberedException lineNumberedException) {
    this.node = node;

    this.message = lineNumberedException.getMessage();

    this.location = lineNumberedException.getLocation();
  }

  public String getNode() {
    return node;
  }

  public Location getLocation() {
    return location;
  }

  public String getMessage() {
    return message;
  }
}
