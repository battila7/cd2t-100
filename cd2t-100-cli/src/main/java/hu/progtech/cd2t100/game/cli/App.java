package hu.progtech.cd2t100.game.cli;

/**
 *	The main class of the CLI application.
 */
public class App {
	private static GameManager gameManager;

	/**
	 *	The entry point of the application.
	 *
	 *	@param args the command line arguments
	 */
	public static void main(String[] args) {
		gameManager = new GameManager();

		gameManager.launch();
	}
}
