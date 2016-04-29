package hu.progtech.cd2t100.game.cli;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

/**
 *  {@code Scene} represents a scene in the CLI session. Along with some static helper
 *  methods it includes one method that must be implemented child classes.
 *  {@link Scene#focus(GameManager)} is called when the {@code Scene} instance
 *  is displayed.
 */
public abstract class Scene {
  /**
   *  Prints the provided string and a line under.
   *
   *  @param str the string to highlight
   */
  public static void printHeading(String str) {
    System.out.println("\n" + str);

    System.out.println(StringUtils.repeat("-", str.length()) + "\n");
  }

  /**
   *  Waits for a valid user input when in a numbered menu.
   *
   *  @param maximalValidChoice The maximal value of the user input. The minimum
   *                            is {@code 1}.
   *  @param scanner the {@code Scanner} the input can be read from
   *
   *  @return the choice
   */
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

  /**
   *  Contains the logic for the scene. Called when the scene is displayed.
   *
   *  @param parent a reference to the parent {@code GameManager} object
   *
   *  @return the scene to be displayed next
   */
  public abstract Scene focus(GameManager parent);
}
