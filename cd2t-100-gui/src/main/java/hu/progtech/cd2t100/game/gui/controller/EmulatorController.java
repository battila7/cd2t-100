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

  private Puzzle puzzle;

  private IOPortController ioPortController;

  private final Map<String, NodeController> nodeControllers;

  private PortMappingController portMappingController;

  public EmulatorController() {
    nodeControllers = new HashMap<>();
  }

  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;

    puzzleNameLabel.setText(puzzle.getName());

    puzzleTaskLabel.setText(puzzle.getTask());

    linkControllers();
  }

  private void linkControllers() {
    ioPortController =
      new IOPortController(puzzle);

    ioPortController.link(ioTabPane);

    portMappingController =
      new PortMappingController(portTable, puzzle);

    portMappingController.attach();

    injectNodes();
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
      NodeController controller = new NodeController(nodeGridPane, descriptor);

      controller.attach();

      nodeControllers.put(descriptor.getGlobalName(), controller);
    }
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
