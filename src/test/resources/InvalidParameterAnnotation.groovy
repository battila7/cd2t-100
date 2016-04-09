import hu.progtech.cd2t100.computation.annotations.*;

import hu.progtech.cd2t100.computation.ParameterType;
import hu.progtech.cd2t100.computation.ExecutionEnvironment;

@Opcode("MPARAMAN")
class MissingParameterAnnotation {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.LABEL) int second) {

  }
}
