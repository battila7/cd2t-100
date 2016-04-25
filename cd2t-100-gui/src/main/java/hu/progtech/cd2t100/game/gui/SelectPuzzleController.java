package hu.progtech.cd2t100.game.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.fxml.FXML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.PuzzleDao;
import hu.progtech.cd2t100.game.model.PuzzleDaoXml;

public class SelectPuzzleController {
  private static final Logger logger = LoggerFactory.getLogger(SelectPuzzleController.class);

  private static final String PUZZLE_XML = "xml/puzzles.xml";

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button instructionsButton;

  @FXML
  private Button helpButton;

  @FXML
  private Button exitButton;

  @FXML
  private VBox puzzlesVBox;

  private GameManager gameManager;

  private PuzzleDao puzzleDao;

  public void setGameManager(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @FXML
  private void handleExitButtonClick() {
    gameManager.requestExit();
  }

  @FXML
  private void handleInstructionsButtonClick() {
    gameManager.changeScene(InstructionsController.class);
  }

  @FXML
  private void initialize() {
    puzzleDao = new PuzzleDaoXml(PUZZLE_XML);

    List<Puzzle> puzzles = puzzleDao.getAllPuzzles();

    if (puzzles == null) {
      logger.error("Could not load puzzles from {}", PUZZLE_XML);

      logger.error("Terminating...");

      Platform.exit();
    }

    for (Puzzle puzzle : puzzleDao.getAllPuzzles()) {
      Pane p = createPuzzleItem(puzzle);

      puzzlesVBox.getChildren().add(p);
    }
  }

  private Pane createPuzzleItem(Puzzle puzzle) {
    Button playButton = new Button("Play");
    playButton.setPrefHeight(45.0);
    playButton.setMaxHeight(Double.MAX_VALUE);

    Label nameLabel = new Label(puzzle.getName());
    nameLabel.setAlignment(Pos.TOP_LEFT);

    Label taskLabel = new Label(puzzle.getTask());
    taskLabel.setAlignment(Pos.TOP_LEFT);
    taskLabel.setWrapText(true);
    taskLabel.setMaxHeight(Double.MAX_VALUE);
    taskLabel.setMaxWidth(Double.MAX_VALUE);

    VBox dataBox = new VBox(5.0);

    dataBox.getChildren().addAll(nameLabel, taskLabel);

    HBox root = new HBox(10.0, playButton, dataBox);
    root.setPrefHeight(85);
    root.setPadding(new Insets(10.0));
    root.setHgrow(dataBox, Priority.ALWAYS);
    root.setMinHeight(Control.USE_PREF_SIZE);

    return root;
  }
}
