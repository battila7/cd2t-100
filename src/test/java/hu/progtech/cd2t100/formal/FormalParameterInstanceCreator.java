package hu.progtech.cd2t100.formal;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

class FormalParameterInstanceCreator implements
  InstanceCreator<FormalParameter> {

  public FormalParameter createInstance(Type type) {
    return new FormalParameter(ParameterType.NUMBER, null);
  }
}
