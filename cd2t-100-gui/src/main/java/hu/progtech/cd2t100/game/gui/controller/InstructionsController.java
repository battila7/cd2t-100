package hu.progtech.cd2t100.game.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.fxml.FXML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.game.model.InstructionDescriptor;

/**
 *  Controller class for the scene showing the known instructions.
 */
public class InstructionsController extends ManagedController {
  private static final Logger logger = LoggerFactory.getLogger(SelectPuzzleController.class);
  
  @FXML
  private Button backButton;

  @FXML
  private VBox instructionsVBox;

  /**
   *  Populates the appropiate UI element with the specified data.
   *
   *  @param list the list to show
   */
  public void populateList(List<InstructionDescriptor> list) {
    for (InstructionDescriptor descriptor : list) {
      Pane p = createInstructionItem(descriptor);

      instructionsVBox.getChildren().add(p);
    }
  }

  @FXML
  private void handleBackButtonClick() {
    gameManager.changeScene(SelectPuzzleController.class);
  }

  private Pane createInstructionItem(InstructionDescriptor descriptor) {
    Label opcodeLabel = new Label(descriptor.getOpcode());
    opcodeLabel.setAlignment(Pos.TOP_LEFT);

    Label descriptionLabel = new Label(descriptor.getDescription());
    descriptionLabel.setAlignment(Pos.TOP_LEFT);
    descriptionLabel.setWrapText(true);
    descriptionLabel.setMaxHeight(Double.MAX_VALUE);
    descriptionLabel.setMaxWidth(Double.MAX_VALUE);

    VBox dataBox = new VBox(5.0);

    dataBox.getChildren().addAll(opcodeLabel, descriptionLabel);

    HBox root = new HBox(10.0, dataBox);
    root.setPrefHeight(85);
    root.setPadding(new Insets(10.0));
    root.setHgrow(dataBox, Priority.ALWAYS);
    root.setMinHeight(Control.USE_PREF_SIZE);

    return root;
  }
}
