package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.emulator.StateChangeRequest;

public class StopCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    gameScene.getEmulator().request(StateChangeRequest.STOP);
  }
}
