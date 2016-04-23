package hu.progtech.cd2t100.game.cli;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

public abstract class Scene {
  public abstract Scene focus(GameManager parent);

  public static void printHeading(String str) {
    System.out.println("\n" + str);

    System.out.println(StringUtils.repeat("-", str.length()) + "\n");
  }

  public static int waitForChoice(int maximalValidChoice, Scanner scanner) {
    String selectedOption = null;

    int integerChoice = 0;

    do {
      try {
        System.out.print("Select an option: ");

        selectedOption = scanner.nextLine();

        integerChoice = Integer.parseInt(selectedOption);

        if ((integerChoice < 1) || (integerChoice > maximalValidChoice)) {
          throw new InputMismatchException();
        }
      } catch (NumberFormatException | InputMismatchException e) {
        System.out.println("Invalid choice! Please try again!\n");

        selectedOption = null;
      } catch (NoSuchElementException | IllegalStateException e) {
        System.out.println("Unable to read.");

        return -1;
      }
    } while (selectedOption == null);

    return integerChoice - 1;
  }
}
