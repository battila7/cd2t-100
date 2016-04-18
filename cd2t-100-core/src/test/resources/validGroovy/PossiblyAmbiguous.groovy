@Opcode("AMB")
class Amb {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int n,
               @Parameter(parameterType = ParameterType.LABEL) String lbl) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int n,
               @Parameter(parameterType = ParameterType.NUMBER) int k) {
    return;
  }
}
