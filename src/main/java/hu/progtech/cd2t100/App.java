package hu.progtech.cd2t100;

import java.util.Scanner;
import java.util.HashSet;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

public class App {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Asm code (A) / Groovy instruction (G):");

		String choice = sc.nextLine();

		if (choice.toUpperCase().charAt(0) == 'A') {
			elementSetAnalysis(sc);
		} else {
			buildInstructionInfo(sc);
		}
	}

	private static void elementSetAnalysis(Scanner sc) {
		CodeElementSet elementSet;

		HashSet<String> registerNameSet = new HashSet<>();
		HashSet<String> portNameSet = new HashSet<>();

		registerNameSet.add("ACC");
		registerNameSet.add("BAK");
		//args[0]);//"ACC: MOV 10 ACC\nzero: MOV 10 LOL\nzero:");

		portNameSet.add("UP");
		portNameSet.add("RIGHT");
		portNameSet.add("DOWN");
		portNameSet.add("LEFT");

		String code = "";

		while (sc.hasNextLine()) {
			code += sc.nextLine() + "\n";
		}

		elementSet =
			CodeFactory.createCodeElementSet(registerNameSet,
																		 	 portNameSet,
																			 code);

		System.out.println(elementSet);
	}

	private static void buildInstructionInfo(Scanner sc) {
		String code = "";

		while (sc.hasNextLine()) {
			code += sc.nextLine();
		}

		try {
			InstructionInfo instructionInfo
				= InstructionLoader.loadInstruction(code);

			System.out.println(instructionInfo);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
