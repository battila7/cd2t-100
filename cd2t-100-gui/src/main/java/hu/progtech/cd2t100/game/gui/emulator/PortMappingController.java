package hu.progtech.cd2t100.game.gui.emulator;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.ObjectProperty;

import hu.progtech.cd2t100.emulator.EmulatorCycleData;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.PortNameMapping;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;
import hu.progtech.cd2t100.game.model.CommunicationPortDescriptor;
import hu.progtech.cd2t100.game.model.NodeDescriptor;

/**
 *  {@code PortMappingController} connects the UI with the
 *  {@code PortMapping}s and refreshes the mappings according to the
 *  latest emulator cycle.
 */
public class PortMappingController {
  private final Puzzle puzzle;

  private TableView<PortMapping> parentTableView;

  private ObservableList<PortMapping> backingList;

  /**
   *  Constructs a new {@code PortMappingController} using the specified
   *  {@code Puzzle} and property. The {@code PortMapping}s are created using
   *  the data from the {@code Puzzle} object. A listener is added to the
   *  passed property which handles the refreshing of the UI.
   *
   *  @param puzzle the {@code Puzzle}
   *  @param emulatorCycleData the property wrapping {@code EmulatorCycleData}
   */
  public PortMappingController(Puzzle puzzle, ObjectProperty<EmulatorCycleData> emulatorCycleData) {
    this.puzzle = puzzle;

    this.parentTableView = parentTableView;

    emulatorCycleData.addListener(
      (observable, oldValue, newValue) -> refresh(newValue)
    );
  }

  /**
   *  Links the {@code PortMappingController} to the specified controls.
   *
   *  @param parentTableView the {@code TableView} to populate with port data
   */
  @SuppressWarnings("unchecked")
  public void link(TableView<PortMapping> parentTableView) {
    this.parentTableView = parentTableView;

    parentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<PortMapping, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(
            new PropertyValueFactory<PortMapping, String>("name"));

    TableColumn<PortMapping, String> fromCol = new TableColumn<>("From");
    fromCol.setCellValueFactory(
            new PropertyValueFactory<PortMapping, String>("from"));

    TableColumn<PortMapping, String> toCol = new TableColumn<>("To");
    toCol.setCellValueFactory(
            new PropertyValueFactory<PortMapping, String>("to"));

    TableColumn<PortMapping, String> valueCol = new TableColumn<>("Value");
    valueCol.setCellValueFactory(
            new PropertyValueFactory<PortMapping, String>("value"));

    parentTableView.getColumns().setAll(nameCol, fromCol, toCol, valueCol);

    populateList();

    parentTableView.setItems(backingList);
  }

  private void populateList() {
    ArrayList<PortMapping> mappingList = new ArrayList<>();

    for (CommunicationPortDescriptor cpd : puzzle.getCommunicationPortDescriptors()) {
      String from = fillFrom(cpd.getGlobalName()),
             to   = fillTo(cpd.getGlobalName());

      mappingList.add(new PortMapping(cpd.getGlobalName(), from, to));
    }

    for (OutputPortDescriptor opd : puzzle.getOutputPortDescriptors()) {
      String from = fillFrom(opd.getGlobalName()),
             to   = "???";

      mappingList.add(new PortMapping(opd.getGlobalName(), from, to));
    }

    for (InputPortDescriptor ipd : puzzle.getInputPortDescriptors()) {
      String from = "???",
             to   = fillTo(ipd.getGlobalName());

      mappingList.add(new PortMapping(ipd.getGlobalName(), from, to));
    }

    backingList = FXCollections.observableArrayList(mappingList);
  }

  private void refresh(EmulatorCycleData emulatorCycleData) {
    Map<String, Integer> portValues = emulatorCycleData.getPortValues();

    for (PortMapping mapping : backingList) {
      Integer value = portValues.get(mapping.getName());

      mapping.setValue(value == null ? "???" : value.toString());
    }
  }

  private String fillFrom(String name) {
    for (NodeDescriptor node : puzzle.getNodeDescriptors()) {
      for (PortNameMapping pm : node.getWriteablePorts()) {
        if (pm.getGlobalName().equals(name)) {
          return node.getGlobalName() + "(" + pm.getLocalName() + ")";
        }
      }
    }

    return "???";
  }

  private String fillTo(String name) {
    for (NodeDescriptor node : puzzle.getNodeDescriptors()) {
      for (PortNameMapping pm : node.getReadablePorts()) {
        if (pm.getGlobalName().equals(name)) {
          return node.getGlobalName() + "(" + pm.getLocalName() + ")";
        }
      }
    }

    return "???";
  }
}
