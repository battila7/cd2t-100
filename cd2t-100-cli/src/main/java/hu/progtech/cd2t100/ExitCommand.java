package hu.progtech.cd2t100;

import hu.progtech.cd2t100.emulator.Emulator;

public class ExitCommand implements CliCommand {
  @Override
  public void execute(Emulator emulator) {
    App.requestExit();
  }
}
