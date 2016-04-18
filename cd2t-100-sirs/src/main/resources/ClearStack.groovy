@Opcode("CLRSTACK")
@Rules([])
class ClearStack {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "STACKPTR") int[] stackPointer)
  {
    stackPointer[0] = 0;
  }
}
