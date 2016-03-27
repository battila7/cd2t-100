package hu.progtech.cd2t100.asm;

final class DuplicateLabelNameException extends LineNumberedException {
  private String labelName;

  public DuplicateLabelNameException(int lineNumber, int columnNumber,
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
           + "\" already exists.";
  }
}
