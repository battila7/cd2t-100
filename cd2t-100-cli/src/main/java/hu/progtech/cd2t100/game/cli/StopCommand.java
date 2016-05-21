package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.emulator.StateChangeRequest;

/**
 *  {@code StopCommand} can be used to send a stop request to the
 *  underlying {@code Emulator} when in the {@code GameScene}'s
 *  main loop.
 */
public class StopCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    gameScene.getEmulator().request(StateChangeRequest.STOP);
  }
}
