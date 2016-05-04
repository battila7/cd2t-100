@Opcode("JGZ")
@Rules([])
class Jgz {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.LABEL) String label,
               @Parameter(parameterType = ParameterType.REGISTER,
                          implicitValue = "ACC") int[] accumulator) {
    if (accumulator[0] > 0) {
      execEnv.jumpToLabel(label);
    }
  }
}
