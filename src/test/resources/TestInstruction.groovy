import hu.progtech.cd2t100.computation.annotations.*;

import hu.progtech.cd2t100.computation.ParameterType;
import hu.progtech.cd2t100.computation.ExecutionEnvironment;

import org.apache.commons.lang3.mutable.MutableInt;

@Opcode("TEST")
@Rules([ "clampat" ])
public class TestInstruction {
  static void apply(ExecutionEnvironment execEnv) {

  }

  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC")
                    int[] acc)
  {

  }

  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.NUMBER)
                    int num,
                    @Parameter(parameterType = ParameterType.WRITE_PORT)
                    MutableInt mint)
  {

  }
}
