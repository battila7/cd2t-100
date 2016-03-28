package hu.progtech.cd2t100.asm;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.RecognitionException;

final class RecognitionWrapperException extends LineNumberedException {
  private RecognitionException wrappedException;

  public RecognitionWrapperException(int lineNumber, int columnNumber,
                                     RecognitionException exception) {
    super(lineNumber, columnNumber);

    this.wrappedException = exception;
  }

  public RecognitionException getWrappedException() {
    return wrappedException;
  }

  @Override
  public String toString() {
    String msg = super.getMessage();

    Token token = wrappedException.getOffendingToken();

    msg += "Unexpected symbol. Expected "
         + wrappedException.getExpectedTokens()
           .toString(wrappedException.getRecognizer().getVocabulary())
         + ".";

    return msg;
  }
}
