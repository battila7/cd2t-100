package hu.progtech.cd2t100.asm;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

class LocationInstanceCreator implements
  InstanceCreator<Location> {

  public Location createInstance(Type type) {
    return new Location(0, 0);
  }
}
