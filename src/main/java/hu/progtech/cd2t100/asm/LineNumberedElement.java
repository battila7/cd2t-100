package hu.progtech.cd2t100.asm;

abstract class LineNumberedElement {
  protected int lineNumber;

  protected int columnNumber;

  public LineNumberedElement(int lineNumber, int columnNumber) {
    this.lineNumber = lineNumber;

    this.columnNumber = columnNumber;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }
}
