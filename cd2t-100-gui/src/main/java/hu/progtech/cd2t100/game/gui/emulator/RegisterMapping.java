package hu.progtech.cd2t100.game.gui.emulator;

import java.util.Arrays;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *  Wrapper class for displaying the data stored in {@code Register}s in
 *  {@code TableView}s.
 */
public class RegisterMapping {
  private String name;

  private int capacity;

  private final StringProperty values;

  /**
   *  Constructs a new {@code RegisterMapping} with the specified name
   *  and capacity. The contents of the register will be set to zero.
   *
   *  @param name the name of the register
   *  @param capacity the capacity of the register
   */
  public RegisterMapping(String name, int capacity) {
    this.name = name;

    this.capacity = capacity;

    values = new SimpleStringProperty(Arrays.toString(new int[capacity]));
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
   *  Gets the capacity.
   *
   *  @return the capacity
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   *  Gets the values.
   *
   *  @return the values
   */
  public String getValues() {
    return values.get();
  }

  /**
   *  Sets the values of the mapping.
   *
   *  @param values the {@code String} representation of the values
   */
  public void setValues(String values) {
    this.values.set(values);
  }

  /**
   *  The {@code String} representation of the
   *  values stored in the mapped {@code Register}.
   *
   *  @see getValues()
   *  @see setValues(String)
   */
  public StringProperty valuesProperty() {
    return values;
  }
}
