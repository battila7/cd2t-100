@Opcode("AMB")
public class AmbiguousInstruction {
  static apply(ExecutionEnvironment execEnv) {

  }

  static apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC")
                    int[] acc)
  {

  }
}
