package hu.progtech.cd2t100.asm;

import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;

import org.apache.commons.lang3.StringUtils;

/**
 * Initiates the lexing and parsing of source codes and collects the data
 * extracted from the code. Its public interface only consists of the
 * {@link createCodeElementSet} method which is responsible for
 * orchestrating the process.
 */
public final class CodeFactory {
  /**
   * Removes trailing whitespaces from the specified text and
   * appends a newline to the end of the this trimmed text. This method is
   * used to ensure that each line of the text ends with a newline character.
   * The method is <b>null-safe</b>.
   *
   * @param programText The text to be sanitized.
   *
   * @return The sanitized text.
   */
  private static String sanitizeText(String programText) {
    /*
     *  null causes only trailing whitespace to be stripped
     *  \n can be safely used, since our grammar is completely agnostic of
     *  the newline-char format.
     */
    return StringUtils.stripEnd(programText, null) + "\n";
  }

  /**
   * Orchestrates the lexing and parsing of the {@code programText} with respect
   * to the specified configuration (parameters). The data extracted from
   * the process is returned afterwards. The returned object also includes
   * the perceived input errors.
   *
   * @param registerNameSet A set containing the usable register names.
   * @param portNameSet A set containing the usable existing port names.
   * @param programText The source code to be processed.
   *
   * @return The instructions, labels, rules and exceptions extracted from
   *         the {@code programText} bundled up.
   */
  public static CodeElementSet createCodeElementSet(Set<String> registerNameSet,
                                                    Set<String> portNameSet,
                                                    String programText) {
    String sanitized = sanitizeText(programText);

    AsmLexer asmLexer = new AsmLexer(new ANTLRInputStream(sanitized));

    AsmParser asmParser = new AsmParser(new CommonTokenStream(asmLexer));

    AsmListenerImpl asmListener =
      new AsmListenerImpl(registerNameSet, portNameSet);

    AsmErrorListener asmErrorListener =
      new AsmErrorListener();

    /*
     *  Remove the default error listener which prints messages to STDERR.
     */
    asmLexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
    asmParser.removeErrorListener(ConsoleErrorListener.INSTANCE);

    asmLexer.addErrorListener(asmErrorListener);
    asmParser.addErrorListener(asmErrorListener);

    asmParser.addParseListener(asmListener);

    asmParser.program();

    return CodeElementSet.fromListeners(asmListener, asmErrorListener);
  }
}
