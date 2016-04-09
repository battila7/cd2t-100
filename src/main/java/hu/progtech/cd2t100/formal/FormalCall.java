package hu.progtech.cd2t100.formal;

import java.lang.reflect.Method;

import java.util.List;

public class FormalCall {
  private final List<FormalParameter> formalParameterList;

  private final Method backingMethod;

  public FormalCall (List<FormalParameter> formalParameterList,
                     Method backingMethod) {
    this.formalParameterList = formalParameterList;

    this.backingMethod = backingMethod;
  }

  public List<FormalParameter> getFormalParameterList() {
    return formalParameterList;
  }

  public Method getBackingMethod() {
    return backingMethod;
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
