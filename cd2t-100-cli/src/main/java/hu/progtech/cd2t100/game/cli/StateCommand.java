package hu.progtech.cd2t100.game.cli;

public class StateCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    System.out.println("Currently in state "
                       + gameScene.getEmulator().getState());
  }
}
