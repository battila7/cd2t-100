@Opcode("ONEMUL")
class OneMul {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult a,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult b,
               @Parameter(parameterType = ParameterType.WRITE_PORT) MutableInt c)
  {
    return;
  }
}
