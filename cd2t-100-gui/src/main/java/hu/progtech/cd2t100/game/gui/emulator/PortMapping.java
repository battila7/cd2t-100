package hu.progtech.cd2t100.game.gui.emulator;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *  Wrapper class for displaying the contents of {@code CommunicationPorts}
 *  in {@code TableView}s.
 */
public class PortMapping {
  private String name;

  private String from;

  private String to;

  private StringProperty value;

  /**
   *  Constructs a new {@code PortMapping} using the specified values.
   *  The {@code value} stored in the {@code PortMapping} will be set to
   *  {@code ???}.
   *
   *  @param name the name of the port
   *  @param from the global name of the {@code Node} on the writable end
   *              of the mapped port
   *  @param to the global name of the {@code Node} on the readable end
   *            of the mapped port
   */
  public PortMapping(String name, String from, String to) {
    this.name = name;

    this.from = from;

    this.to = to;

    this.value = new SimpleStringProperty("???");
  }

  /**
   *  Gets the name.
   *
   *  @return the name
   */
  public String getName() {
    return name;
  }

  /**
   *  Gets the global name of the {@code Node} on the writeable end of
   *  the mapped port.
   *
   *  @return the global name of the {@code Node}
   */
  public String getFrom() {
    return from;
  }

  /**
   *  Gets the global name of the {@code Node} on the readable end of
   *  the mapped port.
   *
   *  @return the global name of the {@code Node}
   */
  public String getTo() {
    return to;
  }

  /**
   *  Gets the stored value.
   *
   *  @return the stored value
   */
  public String getValue() {
    return value.get();
  }

  /**
   *  Sets the stored value.
   *
   *  @param value the value to store
   */
  public void setValue(String value) {
    this.value.set(value);
  }

  /**
   *  The {@code String} representation of the value stored in this
   *  mapping.
   *
   *  @return the property
   */
  public StringProperty valueProperty() {
    return value;
  }
}
