package hu.progtech.cd2t100.asm;

class UnknownArgumentTypeException extends Exception {
  private String argValue;

  public UnknownArgumentTypeException(String argValue) {
    this.argValue = argValue;
  }

  public String getArgValue() {
    return argValue;
  }
}
