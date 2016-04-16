package hu.progtech.cd2t100.formal;

import java.lang.reflect.Method;

import java.util.List;

/**
 *  This class acts as a wrapper around an {@code apply} method. After parameter
 *  matching is done the appropiate {@code FormalCall} serves as the basis of
 *  the newly created {@code Instruction} object. The opcode is not included
 *  in the class, since opcode's are mapped to {@code InstructionInfo} instances.
 *
 *  @see hu.progtech.cd2t100.computation.Instruction
 *  @see InstructionInfo
 */
public class FormalCall {
  private final List<FormalParameter> formalParameterList;

  private final Method backingMethod;

  private final int demandedParams;

  /**
   *  Constructs a new {@code FormalCall} instance wrapping a {@code Method}
   *  object along with its formal parameters.
   *
   *  @param formalParameterList the list of the formal parameters
   *  @param backingMethod the {@code Method} instance
   *  @param demandedParams the number of formal parameters without implicit values
   */
  public FormalCall (List<FormalParameter> formalParameterList,
                     Method backingMethod,
                     int demandedParams) {
    this.formalParameterList = formalParameterList;

    this.backingMethod = backingMethod;

    this.demandedParams = demandedParams;
  }

  /**
   *  Gets the list of formal parameters.
   *
   *  @return the list of formal parameters
   */
  public List<FormalParameter> getFormalParameterList() {
    return formalParameterList;
  }

  /**
   *  Gets the backing method of this {@code FormalCall} instance.
   *
   *  @return the backing methdo
   */
  public Method getBackingMethod() {
    return backingMethod;
  }

  /**
   *  Gets the number of demanded formal parameters.
   *
   *  @return the number of demanded parameters
   */
  public int getDemandedParams() {
    return demandedParams;
  }

  @Override
  public String toString() {
    String str = "M: " + backingMethod.toString();

    str += " Params: (";

    for (FormalParameter f : formalParameterList) {
      str += "[ " +f.toString() + " ]";
    }

    str += ")";

    return str;
  }
}
