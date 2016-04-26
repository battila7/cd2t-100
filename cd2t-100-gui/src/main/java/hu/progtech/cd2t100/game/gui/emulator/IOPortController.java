package hu.progtech.cd2t100.game.gui.emulator;

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
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.ObjectProperty;

import hu.progtech.cd2t100.emulator.EmulatorCycleData;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;

public class IOPortController {
  private final Puzzle puzzle;

  private TabPane parentTabPane;

  private final Map<String, ObservableList<OutputPortValueMapping>> outputMappings;

  private final Map<String, Integer> pointerMap;

  public IOPortController(Puzzle puzzle, ObjectProperty<EmulatorCycleData> emulatorCycleData) {
    this.puzzle = puzzle;

    this.outputMappings = new HashMap<>();

    this.pointerMap = new HashMap<>();

    emulatorCycleData.addListener(
      (observable, oldValue, newValue) -> refresh(newValue)
    );
  }

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
        outputMappings.get(port).get(pointerMap.get(port)).setActual(values.get(port));

        pointerMap.put(port, pointerMap.get(port) + 1);
      }
    }
  }

  private boolean listCanGrow(String port) {
    return pointerMap.get(port) < outputMappings.get(port).size();
  }
}
