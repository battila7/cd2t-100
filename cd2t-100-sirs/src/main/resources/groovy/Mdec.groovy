@Opcode("MDEC")
@Rules([])
class Mdec {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register) {
    for (int i = 0; i < register.length; ++i) {
      --register[i];
    }
  }
}
