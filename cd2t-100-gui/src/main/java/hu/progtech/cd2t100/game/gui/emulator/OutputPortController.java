package hu.progtech.cd2t100.game.gui.emulator;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import hu.progtech.cd2t100.game.model.OutputPortDescriptor;

public class OutputPortController {
  private OutputPortDescriptor descriptor;

  private TabPane parentTabPane;

  private int dataPointer;

  private ObservableList<OutputPortValueMapping> backingList;

  public OutputPortController(TabPane parentTabPane, OutputPortDescriptor descriptor) {
    this.parentTabPane = parentTabPane;

    this.descriptor = descriptor;
  }

  public void attach() {
    Tab tab = new Tab(descriptor.getGlobalName());

    backingList = FXCollections.observableArrayList();

    descriptor.getExpectedContents()
              .stream()
              .forEach(x -> backingList.add(new OutputPortValueMapping(x)));

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

    table.getColumns().addAll(expectedCol, actualCol);

    table.setItems(backingList);

    tab.setContent(table);

    parentTabPane.getTabs().add(tab);
  }

  public void addValue(int value) {
    if (dataPointer >= backingList.size()) {
      return;
    }

    backingList.set(dataPointer, new OutputPortValueMapping(backingList.get(dataPointer).getExpected(), value));

    ++dataPointer;
  }

  public void reset() {
    backingList.stream().forEach(x -> x.setActual(0));

    dataPointer = 0;
  }
}
