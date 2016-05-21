package hu.progtech.cd2t100.game.gui.controller;

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

/**
 *  The manager class responsible for scene changes and shared resources
 *  behind scenes. {@code GameManager} loads the {@code FXML} files and
 *  other resources needed during the application's lifetime. This
 *  includes an {@link hu.progtech.cd2t100.computation.InstructionRegistry}
 *  object that stores the {@code InstructionInfo}s loaded at startup.
 */
public class GameManager {
  private static final Logger logger = LoggerFactory.getLogger(GameManager.class);

  private static final String FONT_PATH = "fonts/Nouveau_IBM.ttf";

  private static final String INSTRUCTIONS_XML = "xml/instructions.xml";

  private static final String[] FXML_FILES =
    { "fxml/SelectPuzzle.fxml", "fxml/Instructions.fxml", "fxml/Emulator.fxml" };

  private Stage stage;

  private InstructionDescriptorDao instructionDescriptorDao;

  private InstructionRegistry instructionRegistry;

  private Map<Class<?>, Scene> sceneMap;

  private Map<Class<?>, ManagedController> controllerMap;

  /**
   *  Constructs a new {@code GameManager} using the specified {@code Stage}.
   *
   *  @param stage the stage
   */
  public GameManager(Stage stage) {
    this.stage = stage;

    sceneMap = new HashMap<>();
    controllerMap = new HashMap<>();
  }

  /**
   *  Initiates the startup process of the {@code GameManager}. After
   *  loading the appropiate resources, the JavaFX platform takes control.
   *  The method may fail and result in the termination of the application
   *  if a fatal {@code I/O} error occurs during the startup.
   */
  public void start() {
    loadResources();

    initializeStage();

    try {
      initializeScenes();
    } catch (IOException e) {
      logger.error("Could not load and initialize scenes.");

      logger.error(e.getMessage());

      logger.error("Terminating...");

      Platform.exit();
    }

    changeScene(SelectPuzzleController.class);

    stage.show();
  }

  /**
   *  Signals a request to terminate the program gracefully.
   */
  public void requestExit() {
    stage.close();
  }

  /**
   *  Changes to the specified scene.
   *
   *  @param sceneClass the scene
   */
  public void changeScene(Class<?> sceneClass) {
    Optional.ofNullable(sceneMap.get(sceneClass))
            .ifPresent(x -> stage.setScene(x));
  }

  /**
   *  Gets the instance for the specified controller class managed by the
   *  {@code GameManager} or {@code null} if it does not exist.
   *
   *  @param controllerClass the controller class
   *
   *  @return an instance of the controller class
   */
  public ManagedController getController(Class<?> controllerClass) {
    return controllerMap.get(controllerClass);
  }

  /**
   *  Gets the stage the {@code GameManager} instance is associated with.
   *
   *  @return the stage
   */
  public Stage getStage() {
    return stage;
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

    stage.centerOnScreen();

    logger.info("Stage initialized");
  }

  private void initializeScenes() throws IOException {
    for (String fxmlPath : FXML_FILES) {
      FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));

      Parent layout = (Parent)fxmlLoader.load();

      ManagedController controller =
        (ManagedController)fxmlLoader.getController();

      controller.setGameManager(this);

      Scene scene = new Scene(layout);

      scene.getStylesheets().add("css/base.css");

      sceneMap.put(controller.getClass(), scene);
      controllerMap.put(controller.getClass(), controller);

      logger.info("Loaded scene from FXML: {}", fxmlPath);
    }

    SelectPuzzleController spCtrl =
      (SelectPuzzleController)controllerMap.get(SelectPuzzleController.class);

    spCtrl.populatePuzzles();

    InstructionsController iCtrl =
      (InstructionsController)controllerMap.get(InstructionsController.class);

    iCtrl.populateList(instructionDescriptorDao.getAllInstructionDescriptors());

    EmulatorController emuCtrl =
      (EmulatorController)controllerMap.get(EmulatorController.class);

    emuCtrl.setInstructionRegistry(instructionRegistry);
  }
}
