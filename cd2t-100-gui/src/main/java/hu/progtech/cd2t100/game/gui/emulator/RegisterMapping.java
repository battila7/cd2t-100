package hu.progtech.cd2t100.game.gui.emulator;

import java.util.Arrays;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class RegisterMapping {
  private String name;

  private int capacity;

  private final StringProperty values;

  public RegisterMapping(String name, int capacity) {
    this.name = name;

    this.capacity = capacity;

    values = new SimpleStringProperty(Arrays.toString(new int[capacity]));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public String getValues() {
    return values.get();
  }

  public void setValues(String values) {
    this.values.set(values);
  }

  public StringProperty valuesProperty() {
    return values;
  }
}
