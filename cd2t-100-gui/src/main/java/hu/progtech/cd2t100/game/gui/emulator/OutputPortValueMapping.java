package hu.progtech.cd2t100.game.gui.emulator;

import javafx.beans.property.SimpleIntegerProperty;

public class OutputPortValueMapping {
  private int expected;
  private final SimpleIntegerProperty actual;

  public OutputPortValueMapping(int expected) {
    this.expected = expected;

    this.actual = new SimpleIntegerProperty();
  }

  public OutputPortValueMapping(int expected, int actual) {
    this.expected = expected;

    this.actual = new SimpleIntegerProperty(actual);
  }

  public void setExpected(int value) {
    expected = value;
  }

  public int getExpected() {
    return expected;
  }

  public void setActual(int value) {
    actual.set(value);
  }

  public int getActual() {
    return actual.get();
  }

  public SimpleIntegerProperty actualProperty() {
    return actual;
  }
}
