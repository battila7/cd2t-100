package hu.progtech.cd2t100.computation;

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
}
