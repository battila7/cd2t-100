@Opcode("SET")
@Rules([])
class Set {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerDestination,
               @Parameter(parameterType = ParameterType.NUMBER) int index,
               @Parameter(parameterType = ParameterType.NUMBER) int number) {
    if ((index >= 1) && (index <= registerDestination.length)) {
      registerDestination[index - 1] = number;
    }
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerDestination,
               @Parameter(parameterType = ParameterType.NUMBER) int index,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerSource) {
    if ((index >= 1) && (index <= registerDestination.length)) {
      registerDestination[index - 1] = registerSource[0];
    }
  }
}
