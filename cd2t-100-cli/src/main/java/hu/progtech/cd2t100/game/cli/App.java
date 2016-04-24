package hu.progtech.cd2t100.game.cli;

public class App {
	private static GameManager gameManager;

	public static void main(String[] args) {
		gameManager = new GameManager();

		gameManager.launch();
	}
}
