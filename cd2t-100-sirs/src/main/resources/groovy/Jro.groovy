@Opcode("JRO")
@Rules([])
class Jro {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int offset) {
    execEnv.jumpRelative(offset);
  }
}
