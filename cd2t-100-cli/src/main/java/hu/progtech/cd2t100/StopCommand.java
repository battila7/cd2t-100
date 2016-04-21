package hu.progtech.cd2t100;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

public class StopCommand implements CliCommand {
  @Override
  public void execute(Emulator emulator) {
    emulator.request(StateChangeRequest.STOP);
  }
}
