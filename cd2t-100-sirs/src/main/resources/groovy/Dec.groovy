@Opcode("DEC")
@Rules([])
class Dec {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register) {
    --register[0];
  }
}
