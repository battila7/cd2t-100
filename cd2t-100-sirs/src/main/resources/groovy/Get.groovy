@Opcode("GET")
@Rules([])
class GET {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerSource,
               @Parameter(parameterType = ParameterType.NUMBER) int index,
               @Parameter(parameterType = ParameterType.REGISTER,
                          implicitValue = "ACC") int[] accumulator) {
    if ((index >= 1) && (index <= registerSource.length)) {
      accumulator[0] = registerSource[index - 1];
    }
  }
}
