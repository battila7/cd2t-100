package hu.progtech.cd2t100.asm;

import java.util.Optional;

final class SyntaxErrorException extends LineNumberedException {
  private Optional<Object> offendingSymbol;

  public SyntaxErrorException(int lineNumber, int columnNumber,
                              Object offendingSymbol) {
    super(lineNumber, columnNumber);

    this.offendingSymbol = Optional.ofNullable(offendingSymbol);
  }

  public Optional<Object> getOffendingSymbol() {
    return offendingSymbol;
  }

  @Override
  public String getMessage() {
    String msg = super.getMessage() + "Syntax error";

    if (offendingSymbol.isPresent()) {
      return msg + "near \""
             + offendingSymbol.toString()
             + "\".";
    } else {
      return msg + ".";
    }
  }
}
