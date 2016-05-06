package hu.progtech.cd2t100.game.gui.controller;

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

/**
 *  Controller class for the scene where the user can select the puzzle they want
 *  to play with.
 */
public class SelectPuzzleController extends ManagedController {
  private static final Logger logger = LoggerFactory.getLogger(SelectPuzzleController.class);

  private static final String PUZZLE_XML = "xml/puzzles.xml";

  @FXML
  private VBox puzzlesVBox;

  @FXML
  private void handleExitButtonClick() {
    gameManager.requestExit();
  }

  @FXML
  private void handleInstructionsButtonClick() {
    gameManager.changeScene(InstructionsController.class);
  }

  /**
   *  Populates the appropiate UI elements with the puzzle data.
   */
  public void populatePuzzles() {
    PuzzleDao puzzleDao = new PuzzleDaoXml(PUZZLE_XML);

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
    EmulatorController emuCtrl =
      (EmulatorController)gameManager.getController(EmulatorController.class);

    Button playButton = new Button("Play");
    playButton.setPrefHeight(45.0);
    playButton.setMinWidth(70.0);
    playButton.setPrefWidth(70.0);
    playButton.setMaxHeight(Double.MAX_VALUE);

    playButton.setOnAction((evt) -> {
      emuCtrl.setPuzzle(puzzle);

      gameManager.changeScene(EmulatorController.class);
    });

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
