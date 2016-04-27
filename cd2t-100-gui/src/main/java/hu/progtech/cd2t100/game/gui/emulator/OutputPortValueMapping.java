package hu.progtech.cd2t100.game.gui.emulator;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *  Wrapper class for the expected and the actual value of an output port.
 *  Similarly to the {@code InputPortValueMapping} class, only used to
 *  supply the appropiate {@code TableView}s with data.
 */
public class OutputPortValueMapping {
  private int expected;
  private final StringProperty actual;

  /**
   *  Constructs a new {@code OutputPortValueMapping} with the specified
   *  expected value.
   *
   *  @param expected the expected value
   */
  public OutputPortValueMapping(int expected) {
    this.expected = expected;

    this.actual = new SimpleStringProperty("???");
  }

  /**
   *  Constructs a new {@code OutputPortValueMapping} with
   *  the specified expected and actual value.
   *
   *  @param expected the expected value
   *  @param actual the actual value
   */
  public OutputPortValueMapping(int expected, String actual) {
    this.expected = expected;

    this.actual = new SimpleStringProperty(actual);
  }

  /**
   *  Gets the expected value.
   *
   *  @return the expected value
   */
  public int getExpected() {
    return expected;
  }

  /**
   *  Sets the actual value.
   *
   *  @param actual the actual value
   */
  public void setActual(String actual) {
    this.actual.set(actual);
  }

  /**
   *  Gets the actual value.
   *
   *  @return the actual value
   */
  public String getActual() {
    return actual.get();
  }

  /**
   *  Gets the property defining the actual value.
   *
   *  @return the property
   */
  public StringProperty actualProperty() {
    return actual;
  }
}
