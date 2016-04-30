@Opcode("ADD")
@Rules([])
class Add {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int n,
               @Parameter(parameterType = ParameterType.REGISTER) int[] reg)
  {
    reg[0] += n;
  }
}
