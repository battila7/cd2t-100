@Opcode("PUSH")
@Rules([])
class Push {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] accumulator,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACK") int[] stack,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACKPTR") int[] stackPointer)
  {
    int stackPtr = stackPointer[0];

    if (stackPtr < stack.length) {
      stack[stackPtr] = accumulator[0];

      stackPointer[0]++;
    }
  }
}
