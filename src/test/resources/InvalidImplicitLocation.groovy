import hu.progtech.cd2t100.computation.annotations.*;

import org.apache.commons.lang3.mutable.MutableInt;

import hu.progtech.cd2t100.computation.ParameterType;
import hu.progtech.cd2t100.computation.ExecutionEnvironment;

@Opcode("INVIMPLLOC")
class InvalidImplicitLocation {
  static void apply(ExecutionEnvironment execEnv,
                    @Parameter(parameterType = ParameterType.REGISTER,
                               implicitValue = "ACC") int[] acc,
                    @Parameter(parameterType = ParameterType.NUMBER) int num)
  {

  }
}
