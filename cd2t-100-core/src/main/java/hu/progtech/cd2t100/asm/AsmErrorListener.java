package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;

/**
 * The error listener class used during the parsing and lexing stage of the
 * {@code Asm} grammar. Whenever an error occurs, the class wraps it
 * into the corresponding exception. After lexing and parsing is done
 * the exceptions can be retrieved and analyzed.
 */
class AsmErrorListener extends BaseErrorListener {
  private List<LineNumberedException> exceptionList;

  /**
   * Constructs a new listener with an empty exception list.
   */
  public AsmErrorListener() {
    exceptionList = new ArrayList<>();
  }

  @Override
  public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line,
              int charPositionInLine, String msg, RecognitionException e) {
    if (e == null) {
      exceptionList.add(new SyntaxErrorException(new Location(line, charPositionInLine),
                                                 offendingSymbol));
    } else {
      exceptionList.add(
        new RecognitionWrapperException(new Location(line, charPositionInLine), e));
    }
  }

  /**
   * Gets the list of exceptions (hopefully empty).
   *
   * @return the list of exceptions occurred during the lexing and parsing
   */
  public List<LineNumberedException> getExceptionList() {
    return exceptionList;
  }
}
