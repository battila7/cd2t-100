package hu.progtech.cd2t100.game.cli;

/**
 *  {@code WelcomeScene} is displayed right after the CLI application's started.
 *  Displays a welcome message.
 */
public class WelcomeScene extends Scene {
  /**
   *  Prints a welcome message to the console.
   *
   *  @param parent a reference to the parent {@code GameManager} object
   *
   *  @return the scene to be displayed next
   */
  public Scene focus(GameManager parent) {
    System.out.println("+--------------+");
    System.out.println("| CD2T-100 CLI |");
    System.out.println("+--------------+\n");

    return new MenuScene();
  }
}
