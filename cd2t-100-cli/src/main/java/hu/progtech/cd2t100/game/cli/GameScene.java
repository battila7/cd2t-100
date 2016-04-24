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

  public GameScene(Puzzle puzzle) {
    this.puzzle = puzzle;

    abortRequested = false;
  }

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

  public void requestAbort() {
    abortRequested = true;
  }

  public void updateEmulatorSourceCodes() {
    try {
      for (Map.Entry<String, String> entry : nodeSourceCodes.entrySet()) {
        emulator.setSourceCode(entry.getKey(), entry.getValue());
      }
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

  public Puzzle getPuzzle() {
    return puzzle;
  }

  public Emulator getEmulator() {
    return emulator;
  }

  public Scanner getStdinScanner() {
    return scanner;
  }

  public String getNodeSourceCode(String globalName) {
    return nodeSourceCodes.get(globalName);
  }

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
