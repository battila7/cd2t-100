@Opcode("MOV")
@Rules([])
class Mov {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerSource,
               @Parameter(parameterType = ParameterType.WRITE_PORT) MutableInt portDestination) {
    portDestination.setValue(registerSource[0]);
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult portSource,
               @Parameter(parameterType = ParameterType.REGISTER) int[] registerDestination) {
    registerDestination[0] = portSource.getValue();
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int number,
               @Parameter(parameterType = ParameterType.REGISTER) int[] register) {
    register[0] = number;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int number,
               @Parameter(parameterType = ParameterType.WRITE_PORT) MutableInt port) {
    port.setValue(number);
  }
}
