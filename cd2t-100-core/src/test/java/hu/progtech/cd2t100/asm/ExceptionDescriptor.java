package hu.progtech.cd2t100.asm;

class ExceptionDescriptor {
  private String name;

  private Location location;

  ExceptionDescriptor() {
    /*
     * no-args constructor
     */
  }

  public String getName() {
    return name;
  }

  public Location getLocation() {
    return location;
  }
}
