package hu.progtech.cd2t100.game.gui.emulator;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Control;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.computation.NodeMemento;

public class NodeController {
  private final Puzzle puzzle;

  private GridPane sourceCodeGridPane;

  private Tab nodeStatusTab;

  // private final Map<String, NodeMapping>  nodeMappings;

  public NodeController(Puzzle puzzle) {
    this.puzzle = puzzle;

    //nodeMappings = new HashMap<>();
  }

  public void link(GridPane sourceCodeGridPane, Tab nodeStatusTab) {
    this.sourceCodeGridPane = sourceCodeGridPane;

    this.nodeStatusTab = nodeStatusTab;

    initGridPane();

    for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
      linkNode(descriptor);
    }
  }

  private void initGridPane() {
    List<NodeDescriptor> nodes = puzzle.getNodeDescriptors();

    int gridRows = nodes.stream()
                        .mapToInt(NodeDescriptor::getRow)
                        .max().getAsInt(),
        gridCols = nodes.stream()
                        .mapToInt(NodeDescriptor::getColumn)
                        .max().getAsInt();

    for (int i = 0; i < gridRows; ++i) {
      sourceCodeGridPane.getRowConstraints().add(new RowConstraints(200));
    }

    for (int i = 0; i < gridCols; ++i) {
      sourceCodeGridPane.getColumnConstraints().add(new ColumnConstraints(200));
    }
  }

  private void linkNode(NodeDescriptor descriptor) {
    VBox container = new VBox();

    Label nameLabel = new Label(descriptor.getGlobalName());
    nameLabel.setAlignment(Pos.CENTER);
    nameLabel.setTextAlignment(TextAlignment.CENTER);
    nameLabel.setMaxWidth(Double.MAX_VALUE);
    nameLabel.setMaxHeight(Control.USE_COMPUTED_SIZE);
    nameLabel.setPadding(new Insets(5.0, 0, 5.0, 0));

    container.getChildren().add(nameLabel);
    container.setVgrow(nameLabel, Priority.ALWAYS);

    TextArea codeArea = new TextArea();
    codeArea.setPrefWidth(200.0);
    codeArea.setPrefHeight(200.0);
    codeArea.setMaxWidth(Double.MAX_VALUE);
    codeArea.setMaxHeight(Double.MAX_VALUE);

    container.getChildren().add(codeArea);
    container.setVgrow(codeArea, Priority.ALWAYS);

    //codeText.bind(codeArea.textProperty());

    sourceCodeGridPane.add(container, descriptor.getColumn() - 1, descriptor.getRow() - 1);
  }

  public String getCodeText() {
    return null;
    //return codeText.get();
  }

  public SimpleStringProperty codeTextProperty() {
    return null;
    //return codeText;
  }
}
