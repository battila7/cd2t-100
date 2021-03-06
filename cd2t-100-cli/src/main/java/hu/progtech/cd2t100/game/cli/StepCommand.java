package hu.progtech.cd2t100.game.cli;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorState;
import hu.progtech.cd2t100.emulator.StateChangeRequest;

/**
 *  {@code StepCommand} can be used to send a step request to the
 *  underlying {@code Emulator} when in the {@code GameScene}'s
 *  main loop.
 */
public class StepCommand implements GameSceneCommand {
  @Override
  public void execute(GameScene gameScene) {
    Emulator emulator = gameScene.getEmulator();

    if (emulator.getState() == EmulatorState.STOPPED) {
      gameScene.updateEmulatorSourceCodes();
    }

    emulator.request(StateChangeRequest.STEP);
  }
}
