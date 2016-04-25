package hu.progtech.cd2t100.game.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.InstructionRegistry;
import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

import hu.progtech.cd2t100.game.model.InstructionDescriptor;
import hu.progtech.cd2t100.game.model.InstructionDescriptorDao;
import hu.progtech.cd2t100.game.model.InstructionDescriptorDaoXml;

public class GameManager {
  private static final Logger logger = LoggerFactory.getLogger(GameManager.class);

  private static final String FONT_PATH = "fonts/Nouveau_IBM.ttf";

  private static final String INSTRUCTIONS_XML = "xml/instructions.xml";

  private Stage stage;

  private InstructionDescriptorDao instructionDescriptorDao;

  private InstructionRegistry instructionRegistry;

  private Map<Class<?>, Scene> sceneMap;

  public GameManager(Stage stage) {
    this.stage = stage;

    sceneMap = new HashMap<>();
  }

  public void start() {
    try {
      loadResources();

      initializeStage();

      initializeScenes();

      changeScene(SelectPuzzleController.class);

      stage.show();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void requestExit() {
    stage.close();
  }

  public void changeScene(Class<?> sceneClass) {
    Optional.ofNullable(sceneMap.get(sceneClass))
            .ifPresent(x -> stage.setScene(x));
  }

  private void loadResources() {
    Font.loadFont(
      this.getClass().getClassLoader().getResourceAsStream(FONT_PATH), 12);

    instructionDescriptorDao =
      new InstructionDescriptorDaoXml(INSTRUCTIONS_XML);

    instructionRegistry = new InstructionRegistry(new HashMap<>());

    loadInstructions();

    logger.info("All resources have been loaded");
  }

  private void loadInstructions() {
		List<InstructionDescriptor> descriptors =
			instructionDescriptorDao.getAllInstructionDescriptors();

    if (descriptors == null) {
      logger.error("Could not load instruction descriptors from {}", INSTRUCTIONS_XML);

      logger.error("Terminating....");

      Platform.exit();
    }

    int loadedCount = 0;

		for (InstructionDescriptor descriptor : descriptors) {
      try {
			  InputStream is = this.getClass().getClassLoader()
                          .getResourceAsStream(descriptor.getGroovyFile());

        if (is == null) {
          logger.warn("Failed to load instruction from {}", descriptor.getGroovyFile());

          logger.warn("Skipping");

          continue;
        }

			  InstructionInfo info = InstructionLoader.loadInstruction(is);

			  instructionRegistry.registerInstruction(info);

        ++loadedCount;
		  } catch (Exception e) {
        logger.warn("Failed to load instruction from {}", descriptor.getGroovyFile());

        logger.warn("Details: {}", e.getMessage());

        logger.warn("Skipping");
      }
    }

    logger.info("{}/{} instructions successfully loaded.", loadedCount, descriptors.size());
  }

  private void initializeStage() {
    stage.setTitle("CD2T-100");

    stage.setResizable(false);

    stage.centerOnScreen();

    logger.info("Stage initialized");
  }

  private void initializeScenes() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();

    String fxmlPath = "fxml/SelectPuzzle.fxml";

    Parent selectPuzzleLayout =
      (Parent)fxmlLoader.load(getClass().getClassLoader().getResourceAsStream(fxmlPath));

    SelectPuzzleController selectPuzzleController =
      (SelectPuzzleController)fxmlLoader.getController();

    selectPuzzleController.setGameManager(this);

    Scene selectPuzzleScene = new Scene(selectPuzzleLayout);

    selectPuzzleScene.getStylesheets().add("css/base.css");

    sceneMap.put(SelectPuzzleController.class, selectPuzzleScene);
  }
}
