@Opcode("MUL")
class Mul {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.NUMBER) int n) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult n) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.WRITE_PORT) MutableInt n) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] n) {
    return;
  }

  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.LABEL) String n) {
    return;
  }
}
