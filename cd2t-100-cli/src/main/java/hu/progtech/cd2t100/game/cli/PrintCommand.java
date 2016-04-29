package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;

/**
 *  The execution of {@code PrintCommand} prints the source code
 *  currently associated with the {@code Node} objects.
 */
public class PrintCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    Puzzle puzzle = gameScene.getPuzzle();

    for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
      System.out.println("Source code of " + descriptor.getGlobalName());

      System.out.println(gameScene.getNodeSourceCode(descriptor.getGlobalName()));

      System.out.println("");
    }
  }
}
