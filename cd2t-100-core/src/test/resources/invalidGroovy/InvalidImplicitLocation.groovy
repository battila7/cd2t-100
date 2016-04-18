@Opcode("INVIMPLLOC")
class InvalidImplicitLocation {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] acc,
                    @Parameter(parameterType = ParameterType.NUMBER) int num)
  {

  }
}
