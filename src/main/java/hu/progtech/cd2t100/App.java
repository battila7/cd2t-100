package hu.progtech.cd2t100;

import java.util.Scanner;
import java.util.HashSet;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;

public class App {

	public static void main(String[] args) {
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

		Scanner sc = new Scanner(System.in);
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

}
