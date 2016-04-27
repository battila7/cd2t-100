package hu.progtech.cd2t100.game.gui.emulator;

/**
 *  Wrapper class for the actual value of an input port. Only used to
 *  supply the appropiate {@code TableView}s with data.
 */
public class InputPortValueMapping {
  private int actual;

  /**
   *  Construct a new {@code InputPortValueMapping} wrapping the specified
   *  value.
   *
   *  @param actual the value to wrap
   */
  public InputPortValueMapping(int actual) {
    this.actual = actual;
  }
  
  /**
   *  Gets the actual value.
   *
   *  @return the actual value
   */
  public int getActual() {
    return actual;
  }
}
