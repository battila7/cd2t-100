package hu.progtech.cd2t100.asm;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents a source code location with its line index and character position in
 * that line. Both line and character indexing starts from <b>0</b>.
 */
public final class Location {
  private final int line;
  private final int charPositionInLine;

  /**
   * Extracts the location of the specified context's starting token.
   *
   * @param ctx The context we want to get the starting position of.
   *
   * @return The location of the starting token.
   */
  public static Location fromParserRuleContext(ParserRuleContext ctx) {
    Token start = ctx.getStart();

    return new Location(start.getLine(), start.getCharPositionInLine());
  }

  /**
   * Constructs a new location using the specified line and character positions.
   * Indexing starts from <b>0</b>.
   * @param line The index of the line.
   * @param charPositionInLine The character's starting position in the line.
   */
  public Location(int line, int charPositionInLine) {
    this.line = line;
    this.charPositionInLine = charPositionInLine;
  }

  public int getLine() {
    return line;
  }

  public int getCharPositionInLine() {
    return charPositionInLine;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Location)) {
      return false;
    }

    Location loc = (Location)o;

    return new EqualsBuilder()
            .append(loc.line, line)
            .append(loc.charPositionInLine, charPositionInLine)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(13, 33)
            .append(line)
            .append(charPositionInLine)
            .toHashCode();
  }
}
