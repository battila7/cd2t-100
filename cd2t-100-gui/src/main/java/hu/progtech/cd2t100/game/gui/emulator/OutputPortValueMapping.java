package hu.progtech.cd2t100.game.gui.emulator;

import javafx.beans.property.SimpleIntegerProperty;

public class OutputPortValueMapping {
  private final SimpleIntegerProperty expected;
  private final SimpleIntegerProperty actual;

  public OutputPortValueMapping(int expected) {
    this.expected = new SimpleIntegerProperty(expected);

    this.actual = new SimpleIntegerProperty();
  }

  public OutputPortValueMapping(int expected, int actual) {
    this.expected = new SimpleIntegerProperty(expected);

    this.actual = new SimpleIntegerProperty(actual);
  }

  public void setExpected(int value) {
    expected.set(value);
  }

  public int getExpected() {
    return expected.get();
  }

  public void setActual(int value) {
    actual.set(value);
  }

  public int getActual() {
    return actual.get();
  }
}
