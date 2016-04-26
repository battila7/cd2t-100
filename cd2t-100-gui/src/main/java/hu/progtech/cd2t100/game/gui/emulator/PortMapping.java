package hu.progtech.cd2t100.game.gui.emulator;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class PortMapping {
  private String name;

  private String from;

  private String to;

  private StringProperty value;

  public PortMapping(String name, String from, String to) {
    this.name = name;

    this.from = from;

    this.to = to;

    this.value = new SimpleStringProperty("???");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getValue() {
    return value.get();
  }

  public void setValue(String value) {
    this.value.set(value);
  }

  public StringProperty valueProperty() {
    return value;
  }
}
