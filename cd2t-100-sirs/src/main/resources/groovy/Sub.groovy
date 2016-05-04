@Opcode("SUB")
@Rules([])
class Sub {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int number,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register)
  {
    register[0] -= number;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerSource,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerDestination)
  {
    registerDestination[0] -= registerSource[0];
  }
}
