package hu.progtech.cd2t100.asm;

import java.util.Optional;

import org.antlr.v4.runtime.Token;

/**
 * This exception type represents a general syntactical error in the
 * input.
 */
public final class SyntaxErrorException extends LineNumberedException {
  private Optional<Object> offendingSymbol;

  public SyntaxErrorException(Location location, Object offendingSymbol) {
    super(location);

    this.offendingSymbol = Optional.ofNullable(offendingSymbol);
  }

  public Optional<Object> getOffendingSymbol() {
    return offendingSymbol;
  }

  @Override
  public String getMessage() {
    String msg = super.getMessage() + "Syntax error ";

    if (offendingSymbol.isPresent()) {
      Object o = offendingSymbol.get();

      if (o instanceof Token) {
        return msg + "near \""
               + ((Token)o).getText()
               + "\".";
      }
    }

    return msg + ".";
  }
}
