@Opcode("JMP")
@Rules([])
class Jmp {
  static apply(ExecutionEnvironment execEnv,
               @Parameter(parameterType = ParameterType.LABEL) String label) {
    execEnv.jumpToLabel(label);
  }
}
