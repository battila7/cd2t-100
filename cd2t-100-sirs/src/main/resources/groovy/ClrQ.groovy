@Opcode("CLRQ")
@Rules([])
class ClrQ {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "QPTR") int[] queuePointer)
  {
    queuePointer[0] = 0;
  }
}
