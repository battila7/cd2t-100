package hu.progtech.cd2t100.game;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("CD2T-100");

    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
