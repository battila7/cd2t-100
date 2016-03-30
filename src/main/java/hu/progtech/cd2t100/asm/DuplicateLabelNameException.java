package hu.progtech.cd2t100.asm;

public final class DuplicateLabelNameException extends LineNumberedException {
  private String labelName;

  public DuplicateLabelNameException(Location location, String labelName) {
    super(location);

    this.labelName = labelName;
  }

  public String getLabelName() {
    return labelName;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Label with name \"" + labelName
           + "\" already exists.";
  }
}
