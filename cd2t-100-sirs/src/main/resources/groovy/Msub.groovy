@Opcode("MSUB")
@Rules([])
class Msub {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int number,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register)
  {
    for (int i = 0; i < register.length; ++i) {
      register[i] -= number;
    }
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerSource,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerDestination)
  {
    for (int i = 0; i < registerDestination.length; ++i) {
      registerDestination[i] -= registerSource[0];
    }
  }
}
