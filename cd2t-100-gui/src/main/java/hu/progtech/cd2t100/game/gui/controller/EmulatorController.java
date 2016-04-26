package hu.progtech.cd2t100.game.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
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
import hu.progtech.cd2t100.game.model.NodeDescriptor;

import hu.progtech.cd2t100.game.gui.emulator.NodeController;
import hu.progtech.cd2t100.game.gui.emulator.PortMapping;
import hu.progtech.cd2t100.game.gui.emulator.PortMappingController;
import hu.progtech.cd2t100.game.gui.emulator.IOPortController;

/**
 *  TODO: Refactor into less classes. There's no controller needed
 *  per port and per node(?).
 *  IOPortController, PortController, NodeController, that's all,
 *  and these should provide update methods through an observable binding.
 *
 *  Replace the SimpleStringProperty declarations with StringProperty.
 */

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

  @FXML
  private GridPane nodeGridPane;

  @FXML
  private TableView<PortMapping> portTable;

  @FXML
  private Tab nodeStatusTab;

  private Puzzle puzzle;

  private IOPortController ioPortController;

  private NodeController nodeController;

  private PortMappingController portMappingController;

  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;

    puzzleNameLabel.setText(puzzle.getName());

    puzzleTaskLabel.setText(puzzle.getTask());

    linkControllers();
  }

  private void linkControllers() {
    ioPortController = new IOPortController(puzzle);

    ioPortController.link(ioTabPane);

    portMappingController = new PortMappingController(puzzle);

    portMappingController.link(portTable);

    nodeController = new NodeController(puzzle);

    nodeController.link(nodeGridPane, nodeStatusTab);
  }

  @FXML
  private void handleRunButtonClick() {
    //outputPortControllers.get("OP1").addValue(11);
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
