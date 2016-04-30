package hu.progtech.cd2t100.game.gui.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXML;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.InstructionRegistry;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorState;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;

import hu.progtech.cd2t100.game.util.EmulatorFactory;

import hu.progtech.cd2t100.game.gui.emulator.NodeController;
import hu.progtech.cd2t100.game.gui.emulator.PortMapping;
import hu.progtech.cd2t100.game.gui.emulator.RegisterMapping;
import hu.progtech.cd2t100.game.gui.emulator.ExceptionMapping;
import hu.progtech.cd2t100.game.gui.emulator.PortMappingController;
import hu.progtech.cd2t100.game.gui.emulator.IOPortController;
import hu.progtech.cd2t100.game.gui.emulator.EmulatorObserverImpl;

/**
 *  Controller class for the Emulator scene. Acts as a supercontroller
 *  over smaller controllers which are responsible for refreshing
 *  smaller parts of the scene, to keep the classes smaller and
 *  isolated.
 */
public class EmulatorController extends ManagedController {
  private static final Logger logger =
    LoggerFactory.getLogger(EmulatorController.class);

  @FXML
  private Label puzzleNameLabel;

  @FXML
  private Label puzzleTaskLabel;

  @FXML
  private Button runClearButton;

  @FXML
  private Button stopButton;

  @FXML
  private Button stepPauseButton;

  @FXML
  private TabPane ioTabPane;

  @FXML
  private GridPane nodeGridPane;

  @FXML
  private TableView<PortMapping> portTable;

  @FXML
  private TabPane statusTabPane;

  @FXML
  private Tab errorTab;

  @FXML
  private Tab nodeStatusTab;

  @FXML
  private TableView<RegisterMapping> nodeRegisterTable;

  @FXML
  private TableView<ExceptionMapping> errorTable;

  @FXML
  private TableColumn<ExceptionMapping, String> errorNodeColumn;

  @FXML
  private TableColumn<ExceptionMapping, String> errorLocationColumn;

  @FXML
  private TableColumn<ExceptionMapping, String> errorMessageColumn;

  private Puzzle puzzle;

  private Emulator emulator;

  private EmulatorObserverImpl emulatorObserver;

  private InstructionRegistry instructionRegistry;

  private IOPortController ioPortController;

  private NodeController nodeController;

  private PortMappingController portMappingController;

  private ObjectProperty<EmulatorCycleData> emulatorCycleData;

  private ReadOnlyObjectProperty<EmulatorState> emulatorState;

  /**
   *  Sets the {@code Puzzle} this scene is backed by. The
   *  {@link hu.progtech.cd2t100.emulator.Emulator} instance
   *  behind this scene is created using the {@code Puzzle} object
   *  specified with this method.
   *
   *  @param puzzle the puzzle
   */
  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;

    this.emulatorCycleData = new SimpleObjectProperty<>();

    initEmulator();

    puzzleNameLabel.setText(puzzle.getName());

    puzzleTaskLabel.setText(puzzle.getTask());

    linkControllers();

    linkErrorTable();

    Stage stage = gameManager.getStage();

    stage.setOnCloseRequest(x -> {
      emulator.request(StateChangeRequest.STOP);

      cleanUp();
    });
  }

  /**
   *  Sets the {@code InstructionRegistry} the {@code Emulator} behind this
   *  scene can use.
   *
   *  @param instructionRegistry the {@code InstructionRegistry}
   */
  public void setInstructionRegistry(InstructionRegistry instructionRegistry) {
    this.instructionRegistry = instructionRegistry;
  }

  private void initEmulator() {
    Map<String, List<Integer>> expectedPortContents = new HashMap<>();

    for (OutputPortDescriptor descriptor : puzzle.getOutputPortDescriptors()) {
      expectedPortContents.put(descriptor.getGlobalName(),
                               clonePortContents(descriptor));
    }

    this.emulatorObserver =
      new EmulatorObserverImpl(
        expectedPortContents,
        ecd -> Platform.runLater(() -> emulatorCycleData.set(ecd)));

    EmulatorFactory.setDefaultClockFrequency(500);

    EmulatorFactory emuFactory = EmulatorFactory.newInstance(instructionRegistry);

    this.emulator = emuFactory.emulatorFromPuzzle(puzzle, emulatorObserver);

    emulatorObserver.initStateProperty();

    emulatorState = emulatorObserver.emulatorStateProperty();

    emulatorState.addListener(
      (observable, oldValue, newValue) -> emulatorStateChanged(oldValue, newValue)
    );
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

  private void linkErrorTable() {
    errorNodeColumn.setCellValueFactory(
            new PropertyValueFactory<ExceptionMapping, String>("node"));

    errorLocationColumn.setCellValueFactory(
            new PropertyValueFactory<ExceptionMapping, String>("location"));

    errorMessageColumn.setCellValueFactory(
            new PropertyValueFactory<ExceptionMapping, String>("message"));
  }

  @FXML
  private void handleRunClearButtonClick() {
    if (runClearButton.getText().equals("Clear Errors")) {
      errorTable.getItems().clear();
    } else {
      updateSourceCodes();
    }

    emulator.request(StateChangeRequest.RUN);
  }

  @FXML
  private void handleStepPauseButtonClick() {
    if (stepPauseButton.getText().equals("Pause")) {
      emulator.request(StateChangeRequest.PAUSE);
    } else {
      updateSourceCodes();

      emulator.request(StateChangeRequest.STEP);
    }
  }

  @FXML
  private void handleStopButtonClick() {
    ioPortController.reset();

    emulator.request(StateChangeRequest.STOP);
  }

  @FXML
  private void handleAbortButtonClick() {
    abort();
  }

  private void cleanUp() {
    ioTabPane.getTabs().clear();

    nodeGridPane.getChildren().clear();

    nodeGridPane.getRowConstraints().clear();
    nodeGridPane.getColumnConstraints().clear();

    nodeRegisterTable.getItems().clear();
    nodeRegisterTable.getColumns().clear();

    runClearButton.setText("Run");
    runClearButton.setDisable(false);

    stepPauseButton.setText("Step");
    stepPauseButton.setDisable(false);

    stopButton.setDisable(true);
  }

  private void updateSourceCodes() {
    if (emulatorState.get() == EmulatorState.STOPPED) {
      nodeController.getSourceCodes()
                    .entrySet()
                    .forEach(e -> emulator.setSourceCode(e.getKey(), e.getValue()));
    }
  }

  private void abort() {
    emulator.request(StateChangeRequest.STOP);

    cleanUp();

    Stage stage = gameManager.getStage();

    /*
     *  Clear the previous handler.
     */
    stage.setOnCloseRequest((x) -> {});

    gameManager.changeScene(SelectPuzzleController.class);
  }

  private void emulatorStateChanged(EmulatorState oldValue, EmulatorState newValue) {
    Platform.runLater(() -> {
      if (newValue == EmulatorState.ERROR) {
        List<ExceptionMapping> exceptions =
          emulator.getNodeExceptionMap().entrySet()
                          .stream()
                          .map(entry -> new ExceptionMapping(entry.getKey(), entry.getValue()))
                          .collect(Collectors.toList());

          emulator.getCodeExceptionMap().entrySet()
                          .stream()
                          .map(entry -> new ExceptionMapping(entry.getKey(), entry.getValue()))
                          .forEach(x -> exceptions.add(x));

        ObservableList<ExceptionMapping> observableExceptions =
          FXCollections.observableArrayList(exceptions);

        errorTable.setItems(observableExceptions);

        statusTabPane.getSelectionModel().select(errorTab);
      } else if (newValue == EmulatorState.SUCCESS) {
        ButtonType backToMenuButtonType =
          new ButtonType("Back to the Menu", ButtonData.OK_DONE);

        Dialog<String> dialog = new Dialog<>();

        dialog.setHeaderText("Success");

        dialog.setContentText("You've successfully completed this puzzle!");

        dialog.getDialogPane().getButtonTypes().add(backToMenuButtonType);

        dialog.showAndWait();

        abort();

        return;
      }

      runClearButton.setDisable((newValue == EmulatorState.RUNNING) ||
                                (newValue == EmulatorState.SUCCESS));

      runClearButton.setText(newValue == EmulatorState.ERROR ? "Clear Errors" : "Run");

      stopButton.setDisable((newValue != EmulatorState.RUNNING) &&
                            (newValue != EmulatorState.PAUSED));

      stepPauseButton.setDisable(newValue == EmulatorState.SUCCESS ||
                                 newValue == EmulatorState.ERROR);

      stepPauseButton.setText(newValue == EmulatorState.RUNNING ? "Pause" : "Step");

      nodeController.setEditable((newValue == EmulatorState.STOPPED) ||
                                 (newValue == EmulatorState.ERROR));
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
