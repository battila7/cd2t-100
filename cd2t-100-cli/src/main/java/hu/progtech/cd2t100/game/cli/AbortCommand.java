package hu.progtech.cd2t100.game.cli;

/**
 *  {@code AbortCommand} can be used to instruct the emulator
 *  to abort i.e.<!-- --> halt and exit.
 */
public class AbortCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    gameScene.requestAbort();
  }
}
