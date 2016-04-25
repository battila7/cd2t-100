package hu.progtech.cd2t100.game.gui.emulator;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import hu.progtech.cd2t100.game.model.InputPortDescriptor;

public class InputPortController {
  private InputPortDescriptor descriptor;

  private TabPane parentTabPane;

  private ObservableList<InputPortValueMapping> backingList;

  public InputPortController(TabPane parentTabPane, InputPortDescriptor descriptor) {
    this.parentTabPane = parentTabPane;

    this.descriptor = descriptor;
  }

  public void attach() {
    Tab tab = new Tab(descriptor.getGlobalName());

    backingList = FXCollections.observableArrayList();

    descriptor.getContents()
              .stream()
              .forEach(x -> backingList.add(new InputPortValueMapping(x)));

    TableView<InputPortValueMapping> table = new TableView<>();

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<InputPortValueMapping, Integer> actualCol = new TableColumn<>("Actual");
    actualCol.setCellValueFactory(
            new PropertyValueFactory<InputPortValueMapping, Integer>("actual"));

    actualCol.setSortable(false);

    table.getColumns().add(actualCol);

    table.setItems(backingList);

    tab.setContent(table);

    parentTabPane.getTabs().add(tab);
  }
}
