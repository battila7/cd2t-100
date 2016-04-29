package hu.progtech.cd2t100.game.cli;

/**
 *  {@code GameSceneCommand} represents a command that can be issued when
 *  the execution is in the {@code GameScene}'s main loop.
 */
public interface GameSceneCommand {
  /**
   *  Executes the command.
   *
   *  @param gameScene reference to the {@code GameScene} the command was issued
   *                   from
   */
  void execute(GameScene gameScene);
}
