package hu.progtech.cd2t100.game.cli;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.util.EmulatorFactory;

/**
 *  {@code GameScene} is the interface between the user and the emulator. Runs
 *  its own game loop that can be controlled with commands.
 */
public class GameScene extends Scene {
  private static final HashMap<String, GameSceneCommand> commands;

  static {
    commands = new HashMap<>();

		commands.put("ABORT", new AbortCommand());
		commands.put("EDIT",  new EditCommand());
    commands.put("PRINT", new PrintCommand());
		commands.put("RUN",   new RunCommand());
		commands.put("STEP",  new StepCommand());
		commands.put("PAUSE", new PauseCommand());
		commands.put("STOP",  new StopCommand());
    commands.put("STATE", new StateCommand());
    commands.put("INFO",  new InfoCommand());
  }

  private Map<String, String> nodeSourceCodes;

  // private Map<String, List<Integer>> outputPortContents;

  private final Puzzle puzzle;

  private Emulator emulator;

  private Scanner scanner;

  private boolean abortRequested;

  /**
   *  Constructs a new {@code GameScene} using the specified {@code Puzzle}.
   *
   *  @param puzzle the {@code Puzzle} to be used
   */
  public GameScene(Puzzle puzzle) {
    this.puzzle = puzzle;

    abortRequested = false;
  }

  /**
   *  Contains the main loop of the {@code GameScene}. Before entering the game
   *  loop this method instantiates a new {@code Emulator} object using the
   *  {@code Puzzle} specified in the constructor. Issuing an {@code AbortCommand}
   *  breaks the game loop.
   *
   *  @param parent a reference to the parent {@code GameManager} object
   *
   *  @return the scene to be displayed next
   */
  public Scene focus(GameManager parent) {
    this.scanner = parent.getStdinScanner();

    emulator = setupEmulator(parent);

    while (!abortRequested) {
      System.out.print("Enter a command: ");

			String command = scanner.nextLine();

			Optional.ofNullable(commands.get(command))
							.ifPresent(x -> x.execute(this));
		}

    emulator.request(StateChangeRequest.STOP);

    return new MenuScene();
  }

  /**
   *  Sends a request to the object to exit from the game loop.
   */
  public void requestAbort() {
    abortRequested = true;
  }

  /**
   *  Updates the source codes stored in the associated {@code Emulator} object for
   *  each {@code Node}.
   */
  public void updateEmulatorSourceCodes() {
    try {
      for (Map.Entry<String, String> entry : nodeSourceCodes.entrySet()) {
        emulator.setSourceCode(entry.getKey(), entry.getValue());
      }
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   *  Gets the {@code Puzzle} used in the {@code GameScene}.
   *
   *  @return the {@code Puzzle}
   */
  public Puzzle getPuzzle() {
    return puzzle;
  }

  /**
   *  Gets the {@code Emulator} running behind the scenes.
   *
   *  @return the {@code Emulator}
   */
  public Emulator getEmulator() {
    return emulator;
  }

  /**
   *  Gets the {@code Scanner} wrapping {@code System.in}.
   *
   *  @return the {@code Scanner}
   */
  public Scanner getStdinScanner() {
    return scanner;
  }

  /**
   *  Gets the source code associated with the specified {@code Node}.
   *
   *  @param globalName the global name of the {@code Node}
   *
   *  @return the source code of the {@code Node} or {@code null} if there's no
   *          {@code Node} with the specified global name
   */
  public String getNodeSourceCode(String globalName) {
    return nodeSourceCodes.get(globalName);
  }

  /**
   *  Sets the source code of the specified {@code Node}.
   *
   *  @param globalName the {@code Node}'s global name
   *  @param sourceCode the source code to be passed to the {@code Node}
   */
  public void setNodeSourceCode(String globalName, String sourceCode) {
    nodeSourceCodes.put(globalName, sourceCode);
  }

	private Emulator setupEmulator(GameManager parent) {
		EmulatorFactory emulatorFactory =
			EmulatorFactory.newInstance(parent.getInstructionRegistry());

    nodeSourceCodes = new HashMap<>();

    for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
      nodeSourceCodes.put(descriptor.getGlobalName(), "");
    }

    Map<String, List<Integer>> outputPortContents = new HashMap<>();

    Map<String, List<Integer>> expectedPortContents = new HashMap<>();

    for (OutputPortDescriptor descriptor : puzzle.getOutputPortDescriptors()) {
      outputPortContents.put(descriptor.getGlobalName(), new ArrayList<>());

      expectedPortContents.put(descriptor.getGlobalName(),
                               clonePortContents(descriptor));
    }

    return emulatorFactory.emulatorFromPuzzle(
            puzzle, new EmulatorObserverImpl(outputPortContents,
                                             expectedPortContents));
	}

  private List<Integer> clonePortContents(OutputPortDescriptor port) {
    ArrayList<Integer> list = new ArrayList<>();

    for (Integer i : port.getExpectedContents()) {
      list.add(new Integer(i));
    }

    return list;
  }
}
