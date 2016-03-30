package hu.progtech.cd2t100.asm;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public final class Location {
  private final int line;
  private final int charInLine;

  public static Location fromParserRuleContext(ParserRuleContext ctx) {
    Token start = ctx.getStart();

    return new Location(start.getLine(), start.getCharPositionInLine());
  }

  public Location(int line, int charInLine) {
    this.line = line;
    this.charInLine = charInLine;
  }

  public int getLine() {
    return line;
  }

  public int getCharPositionInLine() {
    return charInLine;
  }
}
