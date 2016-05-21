package hu.progtech.cd2t100.game.cli;

import java.io.InputStream;

import java.util.Scanner;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.*;
import hu.progtech.cd2t100.computation.io.*;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

import hu.progtech.cd2t100.game.model.InstructionDescriptor;
import hu.progtech.cd2t100.game.model.InstructionDescriptorDao;
import hu.progtech.cd2t100.game.model.InstructionDescriptorDaoXml;

import hu.progtech.cd2t100.game.model.PuzzleDao;
import hu.progtech.cd2t100.game.model.PuzzleDaoXml;

/**
 *  The manager class responsible for controlling transitions and shared data
 *  between scenes.
 */
public class GameManager {
  private static final Logger logger =
    LoggerFactory.getLogger(GameManager.class);

  private static final String INSTRUCTION_XML = "xml/instructions.xml";

  private static final String PUZZLE_XML = "xml/puzzles.xml";

  private final InstructionRegistry instructionRegistry;

  private final Scanner scanner;

  private final InstructionDescriptorDao descriptorDao;

  private final PuzzleDao puzzleDao;

  /**
   *  Constructs a new {@code GameManager} object. Instructions are loaded
   *  and parsed during the construction.
   */
  public GameManager() {
    scanner = new Scanner(System.in);

    descriptorDao =
      new InstructionDescriptorDaoXml(INSTRUCTION_XML);

    puzzleDao =
      new PuzzleDaoXml(PUZZLE_XML);

    instructionRegistry = new InstructionRegistry(new HashMap<>());

    loadInstructions();
  }

  /**
   *  Launches the game loop.
   */
  public void launch() {
    Scene activeScene = new WelcomeScene();

    while (activeScene != null) {
      activeScene = activeScene.focus(this);
    }
  }

  /**
   *  Gets the DAO for {@code InstructionDescriptor}s.
   *
   *  @return the instruction descriptor DAO
   */
  public InstructionDescriptorDao getInstructionDescriptorDao() {
    return descriptorDao;
  }

  /**
   *  Gets the DAO for {@code Puzzle}s.
   *
   *  @return the DAO
   */
  public PuzzleDao getPuzzleDao() {
    return puzzleDao;
  }

  /**
   *  Gets the shared {@code InstructionRegistry} object.
   *
   *  @return the instruction registry
   */
  public InstructionRegistry getInstructionRegistry() {
    return instructionRegistry;
  }

  /**
   *  Gets the {@code Scanner} object wrapping {@code System.in}.
   *
   *  @return the {@code Scanner} wrapping {@code System.in}
   */
  public Scanner getStdinScanner() {
    return scanner;
  }

  private void loadInstructions() {
		try {
			List<InstructionDescriptor> descriptors =
				descriptorDao.getAllInstructionDescriptors();

			for (InstructionDescriptor descriptor : descriptors) {
        System.out.println(descriptor);

				InputStream is =
					this.getClass().getClassLoader()
                         .getResourceAsStream(descriptor.getGroovyFile());

				InstructionInfo info = InstructionLoader.loadInstruction(is);

				instructionRegistry.registerInstruction(info);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
  }
}
