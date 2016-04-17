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

			Node node =
				builder.setMaximumSourceCodeLines(20)
							 .addInstructionRegistry(registry)
							 .addRegister(new Register(1, "ACC"))
							 .build();

			System.out.println("\nPlease enter the program:\n-------------------------------------------------------");

      String code = readCode(sc);

			node.setSourceCode(code);

			List<LineNumberedException> exceptionList = node.buildCodeElementSet();

			if (!exceptionList.isEmpty()) {
				System.err.println(exceptionList);

				return;
			}

			exceptionList = node.buildInstructions();

			if (!exceptionList.isEmpty()) {
				System.err.println(exceptionList);

				return;
			}

			System.out.println("\nNow running:\n-------------------------------------------------------");

			while (true) {
				node.step();

				Thread.sleep(500);
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
		String code = "";

		while (sc.hasNextLine()) {
			code += sc.nextLine() + "\n";
		}

		return code;
	}
}
