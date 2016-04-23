@Opcode("PUSH")
@Rules([])
class Push {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACK") int[] stack,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACKPTR") int[] stackPointer,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] acc)
  {
    int stackPtr = stackPointer[0];

    if (stackPtr < stack.length) {
      stack[stackPtr] = acc[0];

      stackPointer[0]++;
    }
  }
}
