package hu.progtech.cd2t100.asm;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;

class AsmErrorListener extends BaseErrorListener {
  /**
   *  TODO: Implement
   */
  @Override
  public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line,
              int charPositionInLine, String msg, RecognitionException e) {
    System.out.println(msg);
  }
}
