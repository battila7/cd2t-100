package hu.progtech.cd2t100.game.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.game.gui.controller.GameManager;

/**
 *  The main class of the GUI application. Launches the JavaFX platform.
 */
public class App extends Application {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  @Override
  public void start(Stage stage) {
    GameManager gameManager =
      new GameManager(stage);

    gameManager.start();
  }

  /**
   *  The entry point of the application.
   *
   *  @param args the command line arguments
   */
  public static void main(String[] args) {
    logger.info("Application has been started.");

    launch(args);
  }
}
