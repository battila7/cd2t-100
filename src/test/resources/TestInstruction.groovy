@Opcode("TEST")
@Rules([ "clampat" ])
public class TestInstruction {
  static apply(ExecutionEnvironment execEnv) {

  }

  static apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC")
                    int[] acc)
  {

  }

  static apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.NUMBER)
                    int num,
                    @Parameter(parameterType = ParameterType.WRITE_PORT)
                    MutableInt mint)
  {

  }
}
