package hu.progtech.cd2t100;

import java.util.HashMap;
import java.util.HashSet;

import hu.progtech.cd2t100.asm.CodeFactory;

public class App {

	public static void main(String[] args) {
		CodeFactory.createCodeElementSet(new HashSet<String>(),
																		 new HashSet<String>(),
																		 new HashMap<String, String>(),
																		 args[0]);
	}

}
