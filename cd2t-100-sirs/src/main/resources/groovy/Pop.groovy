@Opcode("POP")
@Rules([])
class Pop {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] accumulator,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACK") int[] stack,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACKPTR") int[] stackPointer)
  {
    int stackPtr = stackPointer[0];

    if (stackPtr > 0) {
      accumulator[0] = stack[stackPtr - 1];

      stackPointer[0]--;
    }
  }
}
