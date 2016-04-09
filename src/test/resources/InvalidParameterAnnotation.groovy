@Opcode("MPARAMAN")
class MissingParameterAnnotation {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.LABEL) int second) {

  }
}
