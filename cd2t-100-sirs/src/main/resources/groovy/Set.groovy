@Opcode("SET")
@Rules([])
class Set {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] reg,
               @Parameter(parameterType = ParameterType.NUMBER) int n) {
    reg[0] = n;
  }
}
