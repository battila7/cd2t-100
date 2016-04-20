package hu.progtech.cd2t100;

import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.concurrent.BlockingQueue;

import java.io.InputStream;

import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.computation.*;
import hu.progtech.cd2t100.computation.io.*;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

import hu.progtech.cd2t100.emulator.*;

public class App {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);

			InstructionRegistry registry = new InstructionRegistry(new HashMap<String, String>());

			NodeBuilder builder = new NodeBuilder();

			System.out.println("The following instructions can be used:");

			loadInstructions(registry);

			CommunicationPort cp1 = new CommunicationPort("CP1");
			CommunicationPort cp2 = new CommunicationPort("CP2");

			Node n1 =
				builder.setMaximumSourceCodeLines(20)
							 .setGlobalName("NODE1")
							 .addInstructionRegistry(registry)
							 .addRegister(new Register(1, "ACC"))
							 .addWriteablePort("UP", cp1)
							 .addReadablePort("UP", cp2)
							 .build();

			builder = new NodeBuilder();

		 	Node n2 =
 				builder.setMaximumSourceCodeLines(20)
							 .setGlobalName("NODE2")
 						 	 .addInstructionRegistry(registry)
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
													.setClockFrequency(1000)
													.setObserver(emulatorObserver)
													.build();

			System.out.println("\nPlease enter the program:\n-------------------------------------------------------");

			String code = readCode(sc);

			emulator.setSourceCode("NODE1", code);

			code = readCode(sc);

			emulator.setSourceCode("NODE2", code);

			emulator.request(StateChangeRequest.RUN);

			/*
			 *	Game loop
			 */
			while (true) {
				String line = sc.nextLine();

				if (line.startsWith("EXIT")) {
					emulator.request(StateChangeRequest.STOP);

					break;
				} else if (line.startsWith("STOP")) {
					emulator.request(StateChangeRequest.STOP);
				} else if (line.startsWith("RUN")) {
					System.out.println("\nPlease enter the program:\n-------------------------------------------------------");

					code = readCode(sc);

					emulator.setSourceCode("NODE1", code);

					code = readCode(sc);

					emulator.setSourceCode("NODE2", code);

					emulator.request(StateChangeRequest.RUN);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static void loadInstructions(InstructionRegistry registry) {
		ArrayList<String> names = new ArrayList<>();

		names.add("Print.groovy");
		names.add("Set.groovy");
		names.add("Jmp.groovy");
		names.add("Dec.groovy");
		names.add("Nop.groovy");
		names.add("Jnz.groovy");
		names.add("Jro.groovy");
		names.add("Push.groovy");
		names.add("Pop.groovy");
		names.add("ClearStack.groovy");
		names.add("Mov.groovy");

		try {
			InputStream is;

			for (String s : names) {
				is = App.class.getClassLoader().getResourceAsStream(s);

				InstructionInfo info = InstructionLoader.loadInstruction(is);

				System.err.println(info);

				registry.registerInstruction(info);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
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

	private static String readCode(Scanner sc) {
		String line = "",
					 code = "";

		while (true) {
			line = sc.nextLine() + "\n";

			if (line.startsWith("<>")) {
				break;
			}

			code += line;
		}

		return code;
	}
}
