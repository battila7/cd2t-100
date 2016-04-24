package hu.progtech.cd2t100.game.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

public class SelectPuzzleController {
  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button instructionsButton;

  @FXML
  private Button helpButton;

  @FXML
  private Button exitButton;

  @FXML
  private VBox puzzlesVBox;

  private GameManager gameManager;

  public void setGameManager(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @FXML
  private void handleExitButtonClick() {
    gameManager.requestExit();
  }
}
