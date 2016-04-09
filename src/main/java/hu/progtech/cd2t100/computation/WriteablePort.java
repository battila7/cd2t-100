package hu.progtech.cd2t100.computation;

public interface WriteablePort {
  boolean canWrite();

  void setData(Integer[] data);
}
