@Opcode("Q")
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

    if (queuePtr < queue.length) {
      queue[queuePtr] = accumulator[0];

      queuePointer[0]++;
    }
  }
}
