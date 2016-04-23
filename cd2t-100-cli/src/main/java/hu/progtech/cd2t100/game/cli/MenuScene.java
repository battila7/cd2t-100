package hu.progtech.cd2t100.game.cli;

import java.util.Scanner;

public class MenuScene extends Scene {
  private static final String[] OPTIONS =
    { "Puzzles", "Instructions", "Exit" };

  public Scene focus(GameManager parent) {
    Scanner scanner = parent.getStdinScanner();

    printHeading("Main Menu");

    for (int i = 0; i < OPTIONS.length; ++i) {
      System.out.println((i + 1) + ": " + OPTIONS[i]);
    }

    System.out.println("");

    int selectedOption = waitForChoice(OPTIONS.length, scanner);

    switch (selectedOption) {
      case 0: return new PuzzleSelectScene();
      case 1: return new InstructionsScene();
      default: return null;
    }
  }
}
