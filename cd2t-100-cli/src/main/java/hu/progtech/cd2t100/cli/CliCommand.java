package hu.progtech.cd2t100.cli;

import hu.progtech.cd2t100.emulator.Emulator;

public interface CliCommand {
  void execute(Emulator emulator);
}