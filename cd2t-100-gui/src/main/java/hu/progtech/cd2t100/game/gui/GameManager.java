package hu.progtech.cd2t100.game.gui;

import java.io.IOException;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameManager {
  private static final Logger logger = LoggerFactory.getLogger(GameManager.class);

  private static final String FONT_PATH =
    Paths.get("fonts", "Nouveau_IBM.ttf").toString();

  private Stage stage;

  public GameManager(Stage stage) {
    this.stage = stage;
  }

  public void start() {
    try {
      loadResources();

      initializeStage();

      FXMLLoader fxmlLoader = new FXMLLoader();

      String fxmlPath = Paths.get("fxml", "SelectPuzzle.fxml").toString();

      Parent selectPuzzleLayout =
        (Parent)fxmlLoader.load(getClass().getClassLoader().getResourceAsStream(fxmlPath));

      SelectPuzzleController selectPuzzleController =
        (SelectPuzzleController)fxmlLoader.getController();

      selectPuzzleController.setGameManager(this);

      Scene selectPuzzleScene = new Scene(selectPuzzleLayout);

      // selectPuzzleScene.getStylesheets().add("css/base.css");

      stage.setScene(selectPuzzleScene);

      stage.show();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void requestExit() {
    stage.close();
  }

  private void loadResources() {
    Font.loadFont(
      this.getClass().getClassLoader().getResourceAsStream(FONT_PATH), 12);

    logger.info("All resources have been loaded");
  }

  private void initializeStage() {
    stage.setTitle("CD2T-100");

    stage.setResizable(false);

    stage.centerOnScreen();

    logger.info("Stage initialized");
  }
}
