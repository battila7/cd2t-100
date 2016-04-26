package hu.progtech.cd2t100.game.gui.emulator;

public class InputPortValueMapping {
  private int actual;

  public InputPortValueMapping(int actual) {
    this.actual = actual;
  }

  public void setActual(int value) {
    actual = value;
  }

  public int getActual() {
    return actual;
  }
}
