@Opcode("SET")
@Rules([])
class Set {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] reg,
               @Parameter(parameterType = ParameterType.NUMBER) int index,
               @Parameter(parameterType = ParameterType.NUMBER) int n) {
    if ((index >= 1) && (index <= reg.length)) {
      reg[index - 1] = n;
    }
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] regDest,
               @Parameter(parameterType = ParameterType.NUMBER) int index,
               @Parameter(parameterType = ParameterType.REGISTER) int[] regSrc) {
    if ((index >= 1) && (index <= regDest.length)) {
      regDest[index - 1] = regSrc[0];
    }
  }
}
