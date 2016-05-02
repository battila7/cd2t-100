@Opcode("NEG")
@Rules([])
class Neg {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] n) {
    n[0] = -1 * n[0];

    return;
  }
}
