@Opcode("MNEG")
@Rules([])
class Mneg {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register) {
    for (int i = 0; i < register.length; ++i) {
      register[i] *= -1;
    }
  }
}
