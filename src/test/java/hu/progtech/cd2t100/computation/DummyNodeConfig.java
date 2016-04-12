package hu.progtech.cd2t100.computation;

import java.util.Set;

class DummyNodeConfig {
  private final Set<String> registerSet;

  private final Set<String> readablePortSet;

  private final Set<String> writeablePortSet;

  private final Set<String> labelSet;

  public DummyNodeConfig(Set<String> registerSet,
                         Set<String> readablePortSet,
                         Set<String> writeablePortSet,
                         Set<String> labelSet)
  {
    this.registerSet = registerSet;

    this.readablePortSet = readablePortSet;

    this.writeablePortSet = writeablePortSet;

    this.labelSet = labelSet;
  }

  public Set<String> getRegisterSet() {
    return registerSet;
  }

  public Set<String> getReadablePortSet() {
    return readablePortSet;
  }

  public Set<String> getWriteablePortSet() {
    return writeablePortSet;
  }

  public Set<String> getLabelSet() {
    return labelSet;
  }
}
