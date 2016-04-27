package hu.progtech.cd2t100.game.gui.emulator;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.ObjectProperty;

import hu.progtech.cd2t100.emulator.EmulatorCycleData;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;

/**
 *  The {@code IOPortController} can control a UI fragment showing
 *  the actual input port data and the actual and expected output port
 *  data.
 */
public class IOPortController {
  private final Puzzle puzzle;

  private TabPane parentTabPane;

  private final Map<String, ObservableList<OutputPortValueMapping>> outputMappings;

  private final Map<String, Integer> pointerMap;

  /**
   *  Constructs a new {@code IOPortController} that builds the UI according to the
   *  specified {@code Puzzle} and listens to the changes of the passed cycle data.
   *
   *  @param puzzle the Puzzle
   *  @param emulatorCycleData an {@code ObjectProperty} wrapping {@code EmulatorCycleData}
   */
  public IOPortController(Puzzle puzzle, ObjectProperty<EmulatorCycleData> emulatorCycleData) {
    this.puzzle = puzzle;

    this.outputMappings = new HashMap<>();

    this.pointerMap = new HashMap<>();

    emulatorCycleData.addListener(
      (observable, oldValue, newValue) -> refresh(newValue)
    );
  }

  /**
   *  Links the controller to the specified tab pane.
   *
   *  @param parentTabPane the tab pane
   */
  public void link(TabPane parentTabPane) {
    this.parentTabPane = parentTabPane;

    for (InputPortDescriptor descriptor : puzzle.getInputPortDescriptors()) {
      linkInputPort(descriptor);
    }

    for (OutputPortDescriptor descriptor : puzzle.getOutputPortDescriptors()) {
      linkOutputPort(descriptor);
    }
  }

  private void linkInputPort(InputPortDescriptor descriptor) {
    Tab tab = new Tab(descriptor.getGlobalName());

    ObservableList<InputPortValueMapping> list =
      FXCollections.observableArrayList();

    descriptor.getContents()
              .stream()
              .forEach(x -> list.add(new InputPortValueMapping(x)));

    TableView<InputPortValueMapping> table = new TableView<>();

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<InputPortValueMapping, Integer> actualCol = new TableColumn<>("Actual");
    actualCol.setCellValueFactory(
            new PropertyValueFactory<InputPortValueMapping, Integer>("actual"));

    actualCol.setSortable(false);

    table.getColumns().add(actualCol);

    table.setItems(list);

    tab.setContent(table);

    parentTabPane.getTabs().add(tab);
  }

  private void linkOutputPort(OutputPortDescriptor descriptor) {
    Tab tab = new Tab(descriptor.getGlobalName());

    ObservableList<OutputPortValueMapping> list =
      FXCollections.observableArrayList();

    descriptor.getExpectedContents()
              .stream()
              .forEach(x -> list.add(new OutputPortValueMapping(x)));

    outputMappings.put(descriptor.getGlobalName(), list);

    pointerMap.put(descriptor.getGlobalName(), 0);

    TableView<OutputPortValueMapping> table = new TableView<>();

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<OutputPortValueMapping, Integer> expectedCol = new TableColumn<>("Expected");
    expectedCol.setCellValueFactory(
            new PropertyValueFactory<OutputPortValueMapping, Integer>("expected"));

    expectedCol.setSortable(false);

    TableColumn<OutputPortValueMapping, Integer> actualCol = new TableColumn<>("Actual");
    actualCol.setCellValueFactory(
            new PropertyValueFactory<OutputPortValueMapping, Integer>("actual"));

    actualCol.setSortable(false);

    table.getColumns().setAll(expectedCol, actualCol);

    table.setItems(list);

    tab.setContent(table);

    parentTabPane.getTabs().add(tab);
  }

  /*
   * FIXME
   *  This part should be refactored.
   */
  private void refresh(EmulatorCycleData emulatorCycleData) {
    Map<String, Integer> values = emulatorCycleData.getPortValues();

    for (String port : outputMappings.keySet()) {
      if ((values.get(port) != null) && (listCanGrow(port))) {
        outputMappings.get(port).get(pointerMap.get(port)).setActual(Integer.toString(values.get(port)));

        pointerMap.put(port, pointerMap.get(port) + 1);
      }
    }
  }

  private boolean listCanGrow(String port) {
    return pointerMap.get(port) < outputMappings.get(port).size();
  }
}
