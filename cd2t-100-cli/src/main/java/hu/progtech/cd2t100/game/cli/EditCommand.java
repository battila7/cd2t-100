package hu.progtech.cd2t100.game.cli;

import java.util.Scanner;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;

/**
 *  {@code EditCommand} implements the source code editing functionality for
 *  the emulator.
 */
public class EditCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    Scanner scanner = gameScene.getStdinScanner();

    Puzzle puzzle = gameScene.getPuzzle();

    try {
      for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
        System.out.println("Enter the source code for " + descriptor.getGlobalName());

        String code = readCode(scanner);

        gameScene.setNodeSourceCode(descriptor.getGlobalName(), code);
      }
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

  private String readCode(Scanner sc) {
		String line = "",
					 code = "";

		while (true) {
			line = sc.nextLine() + "\n";

			if (line.startsWith("<>")) {
				break;
			}

			code += line;
		}

		return code;
	}
}
