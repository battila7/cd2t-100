package hu.progtech.cd2t100.game.cli;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorObserver;
import hu.progtech.cd2t100.emulator.EmulatorState;
import hu.progtech.cd2t100.emulator.EmulatorCycleData;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.util.EmulatorFactory;

public class GameScene extends Scene {
  private static final HashMap<String, GameSceneCommand> commands;

  static {
    commands = new HashMap<>();

		commands.put("ABORT -\tSTOPs the emulator and exits to the Main Menu.",
                 new AbortCommand());
		commands.put("EDIT -\tEDIT the source code. Can only be used in STOPPED state.",
                 new EditCommand());
    commands.put("PRINT -\tPRINT the current source code.",
                 new PrintCommand());
		commands.put("RUN -\tInitiates CONTINUOUS execution.",
                 new RunCommand());
		commands.put("STEP -\tInitiates STEPPED execution.",
                 new StepCommand());
		commands.put("PAUSE -\tPAUSEs the execution.",
                 new PauseCommand());
		commands.put("STOP -\tSTOPs the emulator.",
                 new StopCommand());
  }

  private final Puzzle puzzle;

  private Emulator emulator;

  private boolean abortRequested;

  public GameScene(Puzzle puzzle) {
    this.puzzle = puzzle;

    abortRequested = false;
  }

  public Scene focus(GameManager parent) {
    Scanner scanner = parent.getStdinScanner();

    emulator = setupEmulator(parent);

    while (!abortRequested) {
			String command = scanner.nextLine();

			Optional.ofNullable(commands.get(command))
							.ifPresent(x -> x.execute(this));
		}

    return new MenuScene();
  }

  public void requestAbort() {
    abortRequested = true;
  }

	private Emulator setupEmulator(GameManager parent) {
		EmulatorFactory emulatorFactory =
			EmulatorFactory.newInstance(parent.getInstructionRegistry());

    return emulatorFactory.emulatorFromPuzzle(puzzle,
                                              new EmulatorObserverImpl());
	}

	static class EmulatorObserverImpl implements EmulatorObserver {
		private Emulator emulator;

		private Thread updaterThread;

		@Override
		public void onStateChanged(EmulatorState newState) {
			if (newState == EmulatorState.RUNNING) {
				/*
				 *	If in PAUSED state, we reuse the updaterThread.
				 */
				if (updaterThread == null) {
					updaterThread = new Thread(new Updater(emulator.getCycleDataQueue()));

					updaterThread.start();
				}
			} else if (newState == EmulatorState.STOPPED) {
				/*
				 *	If previous state was ERROR, updaterThread is null.
				 */
				if (updaterThread != null) {
					updaterThread.interrupt();

					updaterThread = null;
				}
			} else if (newState == EmulatorState.ERROR) {
				System.out.println("Now in ERROR state because of the following: ");
				System.out.println(emulator.getNodeExceptionMap());
				System.out.println(emulator.getCodeExceptionMap());
			}
		}

		@Override
		public void setEmulator(Emulator emulator) {
			this.emulator = emulator;
		}
	}

	static class Updater implements Runnable {
		private BlockingQueue<EmulatorCycleData> cycleDataQueue;

		public Updater(BlockingQueue<EmulatorCycleData> cycleDataQueue) {
			this.cycleDataQueue = cycleDataQueue;
		}

		@Override
		public void run() {
			try {
				while (true) {
					EmulatorCycleData ecd = cycleDataQueue.take();

					System.out.println(ecd);
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
