package hu.progtech.cd2t100.game.cli;

import java.util.Scanner;

/**
 *  {@code MenuScene} acts as the main menu in the CLI application.
 */
public class MenuScene extends Scene {
  private static final String[] OPTIONS =
    { "Puzzles", "Instructions", "Exit" };

  /**
   *  Displays the available menu options to the user.
   *
   *  @param parent a reference to the parent {@code GameManager} object
   *
   *  @return the scene to be displayed next
   */
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
