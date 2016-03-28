package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;

class AsmErrorListener extends BaseErrorListener {
  private List<LineNumberedException> exceptionList;

  public AsmErrorListener() {
    exceptionList = new ArrayList();
  }

  @Override
  public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line,
              int charPositionInLine, String msg, RecognitionException e) {
    if (e == null) {
      exceptionList.add(new SyntaxErrorException(line, charPositionInLine,
                                                 offendingSymbol));
    } else {
      exceptionList.add(new RecognitionWrapperException(line,
                                                        charPositionInLine, e));
    }
  }

  public List<LineNumberedException> getExceptionList() {
    return exceptionList;
  }
}
