package hu.progtech.cd2t100.game.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;

public class EmulatorController extends ManagedController {
  private static final Logger logger = LoggerFactory.getLogger(EmulatorController.class);

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Label puzzleNameLabel;

  @FXML
  private Label puzzleTaskLabel;

  @FXML
  private TabPane ioTabPane;

  private Puzzle puzzle;

  private final Map<String, OutputPortController> outputPortControllers;

  public EmulatorController() {
    outputPortControllers = new HashMap<>();
  }

  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;

    injectPuzzleData();
  }

  private void injectPuzzleData() {
    puzzleNameLabel.setText(puzzle.getName());

    puzzleTaskLabel.setText(puzzle.getTask());

    injectOutputPorts();
  }

  private void injectOutputPorts() {
    outputPortControllers.clear();

    for (InputPortDescriptor ipd : puzzle.getInputPortDescriptors()) {
      InputPortController ipc = new InputPortController(ioTabPane, ipd);

      ipc.attach();
    }

    for (OutputPortDescriptor opd : puzzle.getOutputPortDescriptors()) {
      OutputPortController opc = new OutputPortController(ioTabPane, opd);

      opc.attach();

      outputPortControllers.put(opd.getGlobalName(), opc);
    }
  }

  @FXML
  private void handleRunButtonClick() {
    outputPortControllers.get("OP1").addValue(11);
  }

  @FXML
  private void handleStepPauseButtonClick() {

  }

  @FXML
  private void handleStopButtonClick() {

  }

  @FXML
  private void handleAbortButtonClick() {

  }
}
