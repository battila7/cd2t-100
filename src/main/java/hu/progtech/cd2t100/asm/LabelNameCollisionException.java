package hu.progtech.cd2t100.asm;

final class LabelNameCollisionException extends LineNumberedException {
  private String labelName;

  public LabelNameCollisionException(int lineNumber, int columnNumber,
                                     String labelName) {
    super(lineNumber, columnNumber);

    this.labelName = labelName;
  }

  public String getLabelName() {
    return labelName;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Label with name \"" + labelName
           + "\" collides with existing port or register name.";
  }
}
