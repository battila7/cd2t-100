@Opcode("JLZ")
@Rules([])
class Jlz {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.LABEL) String label,
               @Parameter(parameterType = ParameterType.REGISTER,
                          implicitValue = "ACC") int[] acc) {
    if (acc[0] < 0) {
      execEnv.jumpToLabel(label);
    }
  }
}
