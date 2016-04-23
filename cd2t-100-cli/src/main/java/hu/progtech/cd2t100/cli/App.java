package hu.progtech.cd2t100.cli;

import java.util.Optional;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.concurrent.BlockingQueue;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.*;
import hu.progtech.cd2t100.computation.io.*;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

import hu.progtech.cd2t100.emulator.*;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	private static Scanner scanner;

	private static boolean exitRequested;

	public static void main(String[] args) {
		try {
			scanner = new Scanner(System.in);

			logger.info("Setting up the emulator...");

			Emulator emulator = setupEmulator();

			logger.info("The emulator is ready! Entering the game loop.");

			printHelp();

			gameLoop(emulator);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static Scanner getStdinScanner() {
		return scanner;
	}

	public static void requestExit() {
		exitRequested = true;
	}

	private static void printHelp() {
		System.out.println("You can control the emulator with the following commands:");
		System.out.println("\tEXIT: STOPs the emulator and exits the program.");
		System.out.println("\tEDIT: EDIT the source code. Can only be used in STOPPED state.");
		System.out.println("\tRUN: Initiates CONTINUOUS execution.");
		System.out.println("\tSTEP: Initiates STEPPED execution.");
		System.out.println("\tPAUSE: PAUSEs the execution.");
		System.out.println("\tSTOP: STOPs the emulator.\n");
	}

	private static void gameLoop(Emulator emulator) {
		Map<String, CliCommand> commands = new HashMap<>();

		commands.put("EXIT", new ExitCommand());
		commands.put("EDIT", new EditCommand());
		commands.put("RUN", new RunCommand());
		commands.put("STEP", new StepCommand());
		commands.put("PAUSE", new PauseCommand());
		commands.put("STOP", new StopCommand());

		while (!exitRequested) {
			String command = scanner.nextLine();

			Optional.ofNullable(commands.get(command))
							.ifPresent(x -> x.execute(emulator));
		}
	}

	private static Emulator setupEmulator() {
		InstructionRegistry registry = new InstructionRegistry(new HashMap<String, String>());

		NodeBuilder builder = new NodeBuilder();

		loadInstructions(registry);

		CommunicationPort cp1 = new CommunicationPort("CP1");
		CommunicationPort cp2 = new CommunicationPort("CP2");

		int[] input = { 10, 20, 30 };

		InputPort ip1 = new InputPort("IP1", input);
		OutputPort op1 = new OutputPort("OP1");

		Node n1 =
			builder.setMaximumSourceCodeLines(20)
						 .setGlobalName("NODE1")
						 .setInstructionRegistry(registry)
						 .addRegister(new Register(1, "ACC"))
						 .addWriteablePort("UP", cp1)
						 .addWriteablePort("OP", op1)
						 .addReadablePort("UP", cp2)
						 .addReadablePort("DOWN", ip1)
						 .build();

		builder = new NodeBuilder();

		Node n2 =
			builder.setMaximumSourceCodeLines(20)
						 .setGlobalName("NODE2")
						 .setInstructionRegistry(registry)
						 .addRegister(new Register(1, "ACC"))
						 .addReadablePort("DOWN", cp1)
						 .addWriteablePort("DOWN", cp2)
						 .build();

		EmulatorObserver emulatorObserver = new EmulatorObserverImpl();

		EmulatorBuilder emulatorBuilder = new EmulatorBuilder();

		Emulator emulator = emulatorBuilder
												.addNode(n1)
												.addNode(n2)
												.addCommunicationPort(cp1)
												.addCommunicationPort(cp2)
												.addCommunicationPort(ip1)
												.addCommunicationPort(op1)
												.setClockFrequency(1000)
												.setObserver(emulatorObserver)
												.build();

		return emulator;
	}

	private static void loadInstructions(InstructionRegistry registry) {
		ArrayList<String> names = new ArrayList<>();

		names.add("groovy/Print.groovy");
		names.add("groovy/Set.groovy");
		names.add("groovy/Jmp.groovy");
		names.add("groovy/Dec.groovy");
		names.add("groovy/Nop.groovy");
		names.add("groovy/Jnz.groovy");
		names.add("groovy/Jro.groovy");
		names.add("groovy/Push.groovy");
		names.add("groovy/Pop.groovy");
		names.add("groovy/ClearStack.groovy");
		names.add("groovy/Mov.groovy");

		try {
			InputStream is;

			for (String s : names) {
				is = App.class.getClassLoader().getResourceAsStream(s);

				InstructionInfo info = InstructionLoader.loadInstruction(is);

				registry.registerInstruction(info);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
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