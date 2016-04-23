package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.game.model.Puzzle;

public class GameScene extends Scene {
  public GameScene(Puzzle puzzle) {

  }

  public Scene focus(GameManager parent) {
    return new MenuScene();
  }
}
