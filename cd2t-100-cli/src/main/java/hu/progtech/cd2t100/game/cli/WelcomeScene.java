package hu.progtech.cd2t100.game.cli;

public class WelcomeScene extends Scene {
  public Scene focus(GameManager parent) {
    System.out.println("+--------------+");
    System.out.println("| CD2T-100 CLI |");
    System.out.println("+--------------+\n");

    return new MenuScene();
  }
}
