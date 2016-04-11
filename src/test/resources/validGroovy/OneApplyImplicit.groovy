@Opcode("ONEIMPL")
class OneMul {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.READ_PORT) ReadResult a,
               @Parameter(parameterType = ParameterType.REGISTER,
                          implicitValue = "ACC") int[] b,
               @Parameter(parameterType = ParameterType.WRITE_PORT,
                          implicitValue = "UP") MutableInt c)
  {
    return;
  }
}
