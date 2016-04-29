package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.emulator.StateChangeRequest;

/**
 *  {@code PauseCommand} can be used to send a pause request to the
 *  underlying {@code Emulator} when in the {@code GameScene}'s
 *  main loop.
 */
public class PauseCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    gameScene.getEmulator().request(StateChangeRequest.PAUSE);
  }
}
