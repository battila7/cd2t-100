package hu.progtech.cd2t100.computation;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

import hu.progtech.cd2t100.formal.ParameterType;

class ArgumentInstanceCreator implements
  InstanceCreator<Argument> {

  public Argument createInstance(Type type) {
    return new Argument(null, ParameterType.NUMBER);
  }
}
