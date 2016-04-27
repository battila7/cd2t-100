package hu.progtech.cd2t100.game.gui.controller;

/**
 *  Abstract base class for controllers managed by a {@code GameManager} instance.
 */
public abstract class ManagedController {
  protected GameManager gameManager;

  /**
   *  Sets the {@code GameManager} instance associated with this
   *  controller.
   *
   *  @param gameManager the {@code gameManager}
   */
  public void setGameManager(GameManager gameManager) {
    this.gameManager = gameManager;
  }
}
