package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;

public class InfoCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    Puzzle puzzle = gameScene.getPuzzle();

    Scene.printHeading(puzzle.getName());

    System.out.println("Task: " + puzzle.getTask());

    System.out.println("Nodes:");

    for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
      System.out.println(descriptor);
    }

    System.out.println("\nInternode ports:");
    System.out.println(puzzle.getCommunicationPortDescriptors());

    System.out.println("\nInput ports:");
    System.out.println(puzzle.getInputPortDescriptors());

    System.out.println("\nOutput ports:");
    System.out.println(puzzle.getOutputPortDescriptors());
  }
}
