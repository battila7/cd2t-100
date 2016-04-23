@Opcode("POP")
@Rules([])
class Pop {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACK") int[] stack,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACKPTR") int[] stackPointer,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] acc)
  {
    int stackPtr = stackPointer[0];

    if (stackPtr > 0) {
      acc[0] = stack[stackPtr - 1];

      stackPointer[0]--;
    }
  }
}
