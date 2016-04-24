package hu.progtech.cd2t100.game.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  @Override
  public void start(Stage stage) {
    GameManager gameManager =
      new GameManager(stage);

    gameManager.start();
  }

  public static void main(String[] args) {
    logger.info("Application has been started.");

    launch(args);
  }
}
