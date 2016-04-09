package hu.progtech.cd2t100.computation;

public interface ReadablePort {
  boolean hasData();

  int getCapacity();

  Integer[] getData();
}