package hu.progtech.cd2t100;

import java.util.Scanner;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.InvalidStateException;

public class EditCommand implements CliCommand {
  @Override
  public void execute(Emulator emulator) {
    System.out.println("\nPlease enter the program:\n-------------------------------------------------------");

    Scanner sc = App.getStdinScanner();

    try {
      String code = readCode(sc);

      emulator.setSourceCode("NODE1", code);

      code = readCode(sc);

      emulator.setSourceCode("NODE2", code);
    } catch (InvalidStateException e) {
      System.out.println(e.getMessage());
    }
  }

  private String readCode(Scanner sc) {
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
