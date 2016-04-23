package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.game.model.InstructionDescriptor;
import hu.progtech.cd2t100.game.model.InstructionDescriptorDao;

public class InstructionsScene extends Scene {
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
