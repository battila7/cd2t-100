package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.game.model.InstructionDescriptor;
import hu.progtech.cd2t100.game.model.InstructionDescriptorDao;

/**
 *  {@code InstructionsScene} displays the instructions that can be used
 *  in the current CLI session.
 */
public class InstructionsScene extends Scene {
  /**
   *  Displays information about the instructions.
   *
   *  @param parent a reference to the parent {@code GameManager} object
   *
   *  @return the scene to be displayed next
   */
  public Scene focus(GameManager parent) {
    printHeading("Instructions");

    InstructionDescriptorDao descriptorDao =
      parent.getInstructionDescriptorDao();

    for (InstructionDescriptor descriptor :
          descriptorDao.getAllInstructionDescriptors())
    {
      System.out.println(descriptor + "\n");
    }

    return new MenuScene();
  }
}
