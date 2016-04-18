@Opcode("PRINT")
@Rules([])
class Print {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int n) {
    println n;

    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] n) {
    println n[0];

    return;
  }
}
