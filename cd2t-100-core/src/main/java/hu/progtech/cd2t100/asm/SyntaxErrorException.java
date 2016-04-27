package hu.progtech.cd2t100.asm;

import java.util.Optional;

import org.antlr.v4.runtime.Token;

/**
 * This exception type represents a general syntactical error in the
 * input.
 */
public final class SyntaxErrorException extends LineNumberedException {
  private Optional<Object> offendingSymbol;

  /**
   *  Constructs a new {@code SyntaxErrorException} with the given location
   *  and offending symbol.
   *
   *  @param location the location of the symbol's occurrence
   *  @param offendingSymbol the symbol causing the exception
   */
  public SyntaxErrorException(Location location, Object offendingSymbol) {
    super(location);

    this.offendingSymbol = Optional.ofNullable(offendingSymbol);
  }

  /**
   *  Returns the symbol causing the exception.
   *
   *  @return the offending symbol
   */
  public Optional<Object> getOffendingSymbol() {
    return offendingSymbol;
  }

  @Override
  public String getMessage() {
    String msg = "Syntax error ";

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
