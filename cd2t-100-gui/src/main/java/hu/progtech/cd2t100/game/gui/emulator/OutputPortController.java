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
  private final OutputPortDescriptor descriptor;

  private final TabPane parentTabPane;

  private int dataPointer;

  private ObservableList<OutputPortValueMapping> backingList;

  public OutputPortController(TabPane parentTabPane, OutputPortDescriptor descriptor) {
    this.parentTabPane = parentTabPane;

    this.descriptor = descriptor;
  }

  @SuppressWarnings("unchecked")
  public void attach() {
    
  }

  public void addValue(int value) {
    if (dataPointer >= backingList.size()) {
      return;
    }

    backingList.get(dataPointer++).setActual(value);
  }

  public void reset() {
    backingList.stream().forEach(x -> x.setActual(0));

    dataPointer = 0;
  }
}
