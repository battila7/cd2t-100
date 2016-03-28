package hu.progtech.cd2t100.asm;

public abstract class LineNumberedException extends Exception {
  protected final int lineNumber;

  protected final int columnNumber;

  public LineNumberedException(int lineNumber, int columnNumber) {
    this.lineNumber = lineNumber;

    this.columnNumber = columnNumber;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }

  @Override
  public String getMessage() {
    return "At line " + lineNumber + ", column " + columnNumber + ": ";
  }
}
