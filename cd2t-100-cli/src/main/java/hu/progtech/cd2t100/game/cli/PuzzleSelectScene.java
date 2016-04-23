package hu.progtech.cd2t100.game.cli;

import java.util.List;
import java.util.Scanner;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.PuzzleDao;

public class PuzzleSelectScene extends Scene {
  public Scene focus(GameManager parent) {
    Scanner scanner = parent.getStdinScanner();

    printHeading("Puzzles");

    PuzzleDao puzzleDao = parent.getPuzzleDao();

    List<Puzzle> puzzles = puzzleDao.getAllPuzzles();

    for (int i = 0; i < puzzles.size(); ++i) {
      System.out.println((i + 1) + ": " + puzzles.get(i).getName());
    }

    System.out.println((puzzles.size() + 1) + ": Back\n");

    int selectedOption = waitForChoice(puzzles.size() + 1, scanner);

    if (selectedOption == -1) {
      return null;
    } else if (selectedOption == puzzles.size()) {
      return new MenuScene();
    } else {
      return new GameScene(puzzles.get(selectedOption));
    }
  }
}
