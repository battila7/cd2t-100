package hu.progtech.cd2t100.asm;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

class ArgumentElementInstanceCreator implements
  InstanceCreator<ArgumentElement> {

  public ArgumentElement createInstance(Type type) {
    return new ArgumentElement(new Location(0, 0), "");
  }
}
