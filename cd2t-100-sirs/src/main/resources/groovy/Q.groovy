@Opcode("Q")
@Rules([])
class Q {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "QUEUE") int[] queue,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "QPTR") int[] queuePointer,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] acc)
  {
    int queuePtr = queuePointer[0];

    if (queuePtr < queue.length) {
      queue[queuePtr] = acc[0];

      queuePointer[0]++;
    }
  }
}
