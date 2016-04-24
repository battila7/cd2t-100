package hu.progtech.cd2t100.game.cli;

public class AbortCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    gameScene.requestAbort();
  }
}
