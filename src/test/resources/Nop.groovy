import hu.progtech.cd2t100.computation.annotations.*;

import hu.progtech.cd2t100.computation.ExecutionEnvironment;

@Opcode("NOP")
class Nop {
  static apply(ExecutionEnvironment execEnv) {
    return;
  }
}
