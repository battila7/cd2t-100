package hu.progtech.cd2t100.game.gui.emulator;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Control;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextArea;

import javafx.beans.property.SimpleStringProperty;

import hu.progtech.cd2t100.game.model.NodeDescriptor;

public class NodeController {
  private final NodeDescriptor descriptor;

  private final GridPane parentGridPane;

  private TextArea codeArea;

  private final SimpleStringProperty codeText;

  public NodeController(GridPane parentGridPane, NodeDescriptor descriptor) {
    this.parentGridPane = parentGridPane;

    this.descriptor = descriptor;

    codeText = new SimpleStringProperty();
  }

  public void attach() {
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

    codeText.bind(codeArea.textProperty());

    parentGridPane.add(container, descriptor.getColumn() - 1, descriptor.getRow() - 1);
  }

  public String getCodeText() {
    return codeText.get();
  }

  public SimpleStringProperty codeTextProperty() {
    return codeText;
  }
}
