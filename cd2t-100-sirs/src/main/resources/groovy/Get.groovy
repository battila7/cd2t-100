@Opcode("GET")
@Rules([])
class GET {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.REGISTER) int[] reg,
               @Parameter(parameterType = ParameterType.NUMBER) int index,
               @Parameter(parameterType = ParameterType.REGISTER,
                          implicitValue = "ACC") int[] acc) {
    if ((index >= 1) && (index <= reg.length)) {
      acc[0] = reg[index - 1];
    }
  }
}
