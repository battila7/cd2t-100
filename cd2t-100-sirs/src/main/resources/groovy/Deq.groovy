@Opcode("DEQ")
@Rules([])
class Q {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] accumulator,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "QUEUE") int[] queue,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "QPTR") int[] queuePointer)
  {
    int queuePtr = queuePointer[0];

    if (queuePtr > 0) {
      accumulator[0] = queue[0];

      for (int i = 0; i < queuePtr; ++i) {
        queue[i] = queue[i + 1];
      }

      queuePointer[0]--;
    }
  }
}
