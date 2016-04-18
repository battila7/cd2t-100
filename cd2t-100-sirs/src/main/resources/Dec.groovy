@Opcode("DEC")
@Rules([])
class Dec {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] n) {
    n[0] = n[0] - 1;

    return;
  }
}
