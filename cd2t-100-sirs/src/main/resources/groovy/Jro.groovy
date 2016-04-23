@Opcode("JRO")
@Rules([])
class Jro {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int n) {
    execEnv.jumpRelative(n);
  }
}
