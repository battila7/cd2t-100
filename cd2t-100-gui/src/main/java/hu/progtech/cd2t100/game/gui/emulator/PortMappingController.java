package hu.progtech.cd2t100.game.gui.emulator;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.PortNameMapping;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;
import hu.progtech.cd2t100.game.model.CommunicationPortDescriptor;
import hu.progtech.cd2t100.game.model.NodeDescriptor;

public class PortMappingController {
  private final Puzzle puzzle;

  private final TableView<PortMapping> parentTableView;

  private ObservableList<PortMapping> backingList;

  public PortMappingController(TableView<PortMapping> parentTableView, Puzzle puzzle) {
    this.puzzle = puzzle;

    this.parentTableView = parentTableView;
  }

  @SuppressWarnings("unchecked")
  public void attach() {
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
      String from = "???",
             to   = fillTo(opd.getGlobalName());

      mappingList.add(new PortMapping(opd.getGlobalName(), from, to));
    }

    for (InputPortDescriptor ipd : puzzle.getInputPortDescriptors()) {
      String from = fillFrom(ipd.getGlobalName()),
             to   = "???";

      mappingList.add(new PortMapping(ipd.getGlobalName(), from, to));
    }

    backingList = FXCollections.observableArrayList(mappingList);
  }

  private String fillFrom(String name) {
    for (NodeDescriptor node : puzzle.getNodeDescriptors()) {
      for (PortNameMapping pm : node.getReadablePorts()) {
        if (pm.getGlobalName().equals(name)) {
          return node.getGlobalName() + "(" + pm.getLocalName() + ")";
        }
      }
    }

    return "???";
  }

  private String fillTo(String name) {
    for (NodeDescriptor node : puzzle.getNodeDescriptors()) {
      for (PortNameMapping pm : node.getWriteablePorts()) {
        if (pm.getGlobalName().equals(name)) {
          return node.getGlobalName() + "(" + pm.getLocalName() + ")";
        }
      }
    }

    return "???";
  }
}
