package hu.progtech.cd2t100.asm;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;

import org.apache.commons.lang3.StringUtils;

public final class CodeFactory {
  private CodeFactory() {

  }

  private static String sanitizeText(String programText) {
    /*
     *  null causes only trailing whitespace to be stripped
     */
    return StringUtils.stripEnd(programText, null) + "\n";
  }

  public static CodeElementSet createCodeElementSet(Set<String> registerNameSet,
                                                    Set<String> portNameSet,
                                                    Map<String, String> ruleMap,
                                                    String programText) {
    String sanitized = sanitizeText(programText);

    AsmLexer asmLexer = new AsmLexer(new ANTLRInputStream(sanitized));

    AsmParser asmParser = new AsmParser(new CommonTokenStream(asmLexer));

    AsmListenerImpl asmListener =
      new AsmListenerImpl(registerNameSet, portNameSet);

    /*
     *  Remove the default error listener which prints messages to STDERR.
     */
    asmParser.removeErrorListener(ConsoleErrorListener.INSTANCE);

    asmParser.addErrorListener(new AsmErrorListener());

    asmParser.addParseListener(asmListener);

    asmParser.program();

    return null;
  }
}
