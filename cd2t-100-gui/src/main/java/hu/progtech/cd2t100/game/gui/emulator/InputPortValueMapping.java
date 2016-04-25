package hu.progtech.cd2t100.game.gui.emulator;

import javafx.beans.property.SimpleIntegerProperty;

public class InputPortValueMapping {
  private final SimpleIntegerProperty actual;

  public InputPortValueMapping(int actual) {
    this.actual = new SimpleIntegerProperty(actual);
  }

  public void setActual(int value) {
    actual.set(value);
  }

  public int getActual() {
    return actual.get();
  }
}
