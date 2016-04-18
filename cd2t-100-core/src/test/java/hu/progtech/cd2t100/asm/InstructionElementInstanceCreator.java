package hu.progtech.cd2t100.asm;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

class InstructionElementInstanceCreator implements
  InstanceCreator<InstructionElement> {

  public InstructionElement createInstance(Type type) {
    return new InstructionElement(new Location(0, 0), "", null);
  }
}
