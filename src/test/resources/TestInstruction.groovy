@Opcode("TEST")
@Rules([ "clampat" ])
public class TestInstruction {
  static void apply(ExecutionEnvironment execEnv)
  {
    return;
  }

  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC")
                    int[] acc)
  {
    return;
  }

  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.NUMBER)
                    int num,
                    @Parameter(parameterType = ParameterType.WRITE_PORT)
                    MutableInt mint)
  {
    return;
  }
}
