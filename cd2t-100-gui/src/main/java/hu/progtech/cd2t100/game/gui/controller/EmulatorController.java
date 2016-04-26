package hu.progtech.cd2t100.game.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.InstructionRegistry;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorObserver;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;

import hu.progtech.cd2t100.game.util.EmulatorFactory;

import hu.progtech.cd2t100.game.gui.emulator.NodeController;
import hu.progtech.cd2t100.game.gui.emulator.PortMapping;
import hu.progtech.cd2t100.game.gui.emulator.RegisterMapping;
import hu.progtech.cd2t100.game.gui.emulator.PortMappingController;
import hu.progtech.cd2t100.game.gui.emulator.IOPortController;
import hu.progtech.cd2t100.game.gui.emulator.EmulatorObserverImpl;

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

  @FXML
  private TableView<RegisterMapping> nodeRegisterTable;

  private Puzzle puzzle;

  private Emulator emulator;

  private EmulatorObserver emulatorObserver;

  private InstructionRegistry instructionRegistry;

  private IOPortController ioPortController;

  private NodeController nodeController;

  private PortMappingController portMappingController;

  private ObjectProperty<EmulatorCycleData> emulatorCycleData;

  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;

    this.emulatorCycleData = new SimpleObjectProperty<>();

    initEmulator();

    puzzleNameLabel.setText(puzzle.getName());

    puzzleTaskLabel.setText(puzzle.getTask());

    linkControllers();
  }

  public void setInstructionRegistry(InstructionRegistry instructionRegistry) {
    this.instructionRegistry = instructionRegistry;
  }

  private void initEmulator() {
    Map<String, List<Integer>> outputPortContents = new HashMap<>();

    Map<String, List<Integer>> expectedPortContents = new HashMap<>();

    for (OutputPortDescriptor descriptor : puzzle.getOutputPortDescriptors()) {
      outputPortContents.put(descriptor.getGlobalName(), new ArrayList<>());

      expectedPortContents.put(descriptor.getGlobalName(),
                               clonePortContents(descriptor));
    }

    this.emulatorObserver =
      new EmulatorObserverImpl(outputPortContents,
                               expectedPortContents,
                               ecd -> refresh(ecd));

    EmulatorFactory emuFactory = EmulatorFactory.newInstance(instructionRegistry);

    this.emulator = emuFactory.emulatorFromPuzzle(puzzle, emulatorObserver);
  }

  private void linkControllers() {
    ioPortController = new IOPortController(puzzle, emulatorCycleData);

    ioPortController.link(ioTabPane);

    logger.info("IOPortController linked");

    portMappingController = new PortMappingController(puzzle, emulatorCycleData);

    portMappingController.link(portTable);

    logger.info("PortMappingController linked");

    nodeController = new NodeController(puzzle, emulatorCycleData);

    nodeController.link(nodeGridPane, nodeStatusTab, nodeRegisterTable);

    logger.info("NodeController linked");
  }

  @FXML
  private void handleRunButtonClick() {
    nodeController.getSourceCodes()
                  .entrySet()
                  .forEach(e -> emulator.setSourceCode(e.getKey(), e.getValue()));

    emulator.request(StateChangeRequest.RUN);
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

  private void refresh(EmulatorCycleData ecd) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        emulatorCycleData.set(ecd);
      }
    });
  }

  private List<Integer> clonePortContents(OutputPortDescriptor port) {
    ArrayList<Integer> list = new ArrayList<>();

    for (Integer i : port.getExpectedContents()) {
      list.add(new Integer(i));
    }

    return list;
  }
}
