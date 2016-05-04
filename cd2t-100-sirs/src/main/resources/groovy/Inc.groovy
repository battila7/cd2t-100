@Opcode("INC")
@Rules([])
class Inc {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register) {
    ++register[0];
  }
}
