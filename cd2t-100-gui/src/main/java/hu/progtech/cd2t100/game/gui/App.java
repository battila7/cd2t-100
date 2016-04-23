package hu.progtech.cd2t100.gui.game;

import javafx.application.Application;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.game.model.*;

public class App extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("CD2T-100");

    stage.show();
  }

  public static void main(String[] args) {
    InstructionDescriptorDao idd =
      new InstructionDescriptorDaoXml("xml/instructions.xml");

    System.out.println(idd.getAllInstructionDescriptors());

    PuzzleDao pd = new PuzzleDaoXml("xml/puzzles.xml");

    System.out.println(pd.getAllPuzzles());

    launch(args);
  }
}
