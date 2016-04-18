@Opcode("ONE")
class One {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult n) {
    return;
  }
}
