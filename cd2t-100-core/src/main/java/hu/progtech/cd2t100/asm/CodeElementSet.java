package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Stores the data extracted from a lexing and parsing stage. This data includes
 * the exceptions, instruction, labels and rules. Exceptions include both
 * syntactical and semantical input errors. Produced by the {@code CodeFactory}
 * class.
 *
 * @see CodeFactory
 */
public final class CodeElementSet {
  private final List<LineNumberedException> exceptionList;

  private final List<InstructionElement> instructionList;

  private final Map<String, Integer> labelMap;

  private final Map<String, String> ruleMap;

  /**
   * Constructs a new element set holding the specified data.
   *
   * @param exceptionList the exceptions to be stored
   * @param instructionList the instructions to be stored
   * @param labelMap the labels to be stored
   * @param ruleMap the rules to be stored
   */
  CodeElementSet(List<LineNumberedException> exceptionList,
                 List<InstructionElement> instructionList,
                 Map<String, Integer> labelMap,
                 Map<String, String> ruleMap) {
    this.exceptionList = exceptionList;

    this.instructionList = instructionList;

    this.labelMap = labelMap;

    this.ruleMap = ruleMap;
  }

  /**
   * Creates a new element set from the data fetched from the specified
   * listeners. This method provides a more comfortable way to create a new
   * {@code CodeElementSet}.
   *
   * @param listener the listener
   * @param errorListener the error listener
   *
   * @return the {@code CodeElementSet} instance storing the data extracted from the listeners
   */
  public static CodeElementSet fromListeners(AsmListenerImpl listener,
                                             AsmErrorListener errorListener) {
    List<LineNumberedException> lst = new ArrayList<>();

    lst.addAll(listener.getExceptionList());
    lst.addAll(errorListener.getExceptionList());

    return new CodeElementSet(lst,
                              listener.getInstructionList(),
                              listener.getLabelMap(),
                              listener.getRuleMap());
  }

  /**
   * Returns whether any exception has occurred.
   *
   * @return {@code true} if no exception has occurred {@code false} otherwise
   */
  public boolean isExceptionOccurred() {
    return exceptionList.size() > 0;
  }

  /**
   *  Returns the list of exceptions.
   *
   *  @return the exception list
   */
  public List<LineNumberedException> getExceptionList() {
    return exceptionList;
  }

  /**
   *  Returns the list of instructions.
   *
   *  @return the instruction list
   */
  public List<InstructionElement> getInstructionList() {
    return instructionList;
  }

  /**
   * Returns the map of labels.
   *
   * @return the map of labels
   */
  public Map<String, Integer> getLabelMap() {
    return labelMap;
  }

  /**
   *  Returns the map of preprocessor rules.
   *
   *  @return the map of preprocessor rules
   */
  public Map<String, String> getRuleMap() {
    return ruleMap;
  }

  public String toString() {
    String msg = "Instructions:\n";

    for (InstructionElement elem : instructionList) {
      msg += "\t" + elem.toString() + "\n";
    }

    msg += "Labels:\n\t"
         + labelMap.toString() + "\n";

    msg += "Rules:\n\t"
         + ruleMap.toString() + "\n";

    msg += "Exceptions:\n";

    for (LineNumberedException e : exceptionList) {
      msg += "\t" + e.getMessage() + "\n";
    }

    return msg;
  }
}
