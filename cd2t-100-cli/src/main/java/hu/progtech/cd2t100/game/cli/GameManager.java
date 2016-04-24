package hu.progtech.cd2t100.game.cli;

import java.io.InputStream;

import java.nio.file.Paths;

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

public class GameManager {
  private static final Logger logger =
    LoggerFactory.getLogger(GameManager.class);

  private static final String INSTRUCTION_XML =
    Paths.get("xml", "instructions.xml").toString();

  private static final String PUZZLE_XML =
    Paths.get("xml", "puzzles.xml").toString();

  private final InstructionRegistry instructionRegistry;

  private final Scanner scanner;

  private final InstructionDescriptorDao descriptorDao;

  private final PuzzleDao puzzleDao;

  public GameManager() {
    scanner = new Scanner(System.in);

    descriptorDao =
      new InstructionDescriptorDaoXml(INSTRUCTION_XML);

    puzzleDao =
      new PuzzleDaoXml(PUZZLE_XML);

    instructionRegistry = new InstructionRegistry(new HashMap<>());

    loadInstructions();
  }

  public void launch() {
    Scene activeScene = new WelcomeScene();

    while (activeScene != null) {
      activeScene = activeScene.focus(this);
    }
  }

  public InstructionDescriptorDao getInstructionDescriptorDao() {
    return descriptorDao;
  }

  public PuzzleDao getPuzzleDao() {
    return puzzleDao;
  }

  public InstructionRegistry getInstructionRegistry() {
    return instructionRegistry;
  }

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
