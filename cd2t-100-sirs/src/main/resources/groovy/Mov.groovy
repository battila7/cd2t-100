@Opcode("MOV")
@Rules([])
class Mov {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] r,
               @Parameter(parameterType = ParameterType.WRITE_PORT) MutableInt p) {
    p.setValue(r[0]);
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult p,
               @Parameter(parameterType = ParameterType.REGISTER) int[] r) {
    r[0] = p.getValue();
  }
}
