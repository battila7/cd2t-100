package hu.progtech.cd2t100;

import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.InputStream;

import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.computation.*;
import hu.progtech.cd2t100.computation.io.*;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

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

			cp1.setSourceNode(n1);


			builder = new NodeBuilder();

		 	Node n2 =
 				builder.setMaximumSourceCodeLines(20)
							 .setGlobalName("NODE2")
 						 	 .addInstructionRegistry(registry)
 						 	 .addRegister(new Register(1, "ACC"))
							 .addReadablePort("DOWN", cp1)
							 .addWriteablePort("DOWN", cp2)
 						 	 .build();

			cp2.setSourceNode(n2);

			System.out.println("\nPlease enter the program:\n-------------------------------------------------------");

			n1.setSourceCode(readCode(sc));

			List<LineNumberedException> exceptionList = n1.buildCodeElementSet();

			if (!exceptionList.isEmpty()) {
				System.err.println(exceptionList);

				return;
			}

			exceptionList = n1.buildInstructions();

			if (!exceptionList.isEmpty()) {
				System.err.println(exceptionList);

				return;
			}

			n2.setSourceCode(readCode(sc));

			exceptionList = n2.buildCodeElementSet();

			if (!exceptionList.isEmpty()) {
				System.err.println(exceptionList);

				return;
			}

			exceptionList = n2.buildInstructions();

			if (!exceptionList.isEmpty()) {
				System.err.println(exceptionList);

				return;
			}

			System.out.println("\nNow running:\n-------------------------------------------------------");

			long cycle = 0;

			while (true) {
				System.out.println("Cycle " + cycle++);

				cp1.step();
				cp2.step();

				n1.step();
				n2.step();

				sc.nextLine();
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
