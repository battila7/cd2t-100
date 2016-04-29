package hu.progtech.cd2t100.game.gui.emulator;

import hu.progtech.cd2t100.asm.LineNumberedException;
import hu.progtech.cd2t100.asm.Location;

/**
 *  Wrapper class for displaying {@code Exception}s and {@code LineNumberedException}s
 *  produced by {@code Node}s in {@code TableView}s.
 */
public class ExceptionMapping {
  private final String node;

  private final Location location;

  private final String message;

  /**
   *  Constructs a new {@code ExceptionMapping} using the specified
   *  node name and exception. The default (0, 0) {@code Location} is
   *  going to be set for the newly created mapping.
   *
   *  @param node the name of the {@code Node} throwing the exception
   *  @param exception the exception
   */
  public ExceptionMapping(String node, Exception exception) {
    this.node = node;

    this.message = exception.getMessage();

    this.location = new Location(0, 0);
  }

  /**
   *  Constructs a new {@code ExceptionMapping} using the specified node name
   *  and exception.
   *
   *  @param node the name of the {@code Node} throwing the exception
   *  @param lineNumberedException the exception
   */
  public ExceptionMapping(String node, LineNumberedException lineNumberedException) {
    this.node = node;

    this.message = lineNumberedException.getMessage();

    this.location = lineNumberedException.getLocation();
  }

  /**
   *  Gets the node's name.
   *
   *  @return the node's name
   */
  public String getNode() {
    return node;
  }

  /**
   *  Gets the location.
   *
   *  @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   *  Gets the message of the mapped exception.
   *
   *  @return the message
   */
  public String getMessage() {
    return message;
  }
}
