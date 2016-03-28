package hu.progtech.cd2t100;

import java.util.HashMap;
import java.util.HashSet;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;

public class App {

	public static void main(String[] args) {
		CodeElementSet elementSet;

		HashSet<String> registerNameSet = new HashSet<>();

		registerNameSet.add("ACC");

		elementSet =
			CodeFactory.createCodeElementSet(registerNameSet,
																		 	 new HashSet<String>(),
																		 	 new HashMap<String, String>(),
																		 	 args[0]);//"ACC: MOV 10 ACC\nzero: MOV 10 LOL\nzero:");

		elementSet.getExceptionList()
							.stream()
							.forEach(e -> {
								System.out.println(e.getMessage());
							});
	}

}
