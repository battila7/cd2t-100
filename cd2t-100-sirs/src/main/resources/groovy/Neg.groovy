@Opcode("NEG")
@Rules([])
class Neg {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register) {
    register[0] *= -1;
  }
}
