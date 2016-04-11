@Opcode("MXD")
@Rules(["place", "holder", "rule"])
class Mixed {
  static apply(ExecutionEnvironment execEnv) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.LABEL) String label) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
              @Parameter(parameterType = ParameterType.NUMBER) int n,
              @Parameter(parameterType = ParameterType.NUMBER,
                         implicitValue = "100") int i)
  {
    return;
  }
}
