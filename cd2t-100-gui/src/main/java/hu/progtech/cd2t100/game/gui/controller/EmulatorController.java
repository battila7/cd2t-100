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
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;
import hu.progtech.cd2t100.game.model.NodeDescriptor;

import hu.progtech.cd2t100.game.gui.emulator.InputPortController;
import hu.progtech.cd2t100.game.gui.emulator.OutputPortController;
import hu.progtech.cd2t100.game.gui.emulator.NodeController;

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

    injectNodes();
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

  private void injectNodes() {
    List<NodeDescriptor> nodes = puzzle.getNodeDescriptors();

    int gridRows = nodes.stream()
                        .mapToInt(NodeDescriptor::getRow)
                        .max().getAsInt(),
        gridCols = nodes.stream()
                        .mapToInt(NodeDescriptor::getColumn)
                        .max().getAsInt();

    for (int i = 0; i < gridRows; ++i) {
      nodeGridPane.getRowConstraints().add(new RowConstraints(200));
    }

    for (int i = 0; i < gridCols; ++i) {
      nodeGridPane.getColumnConstraints().add(new ColumnConstraints(200));
    }

    for (NodeDescriptor descriptor : nodes) {
      NodeController nc = new NodeController(nodeGridPane, descriptor);

      nc.attach();
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
