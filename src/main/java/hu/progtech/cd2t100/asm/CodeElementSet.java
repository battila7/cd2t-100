package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public final class CodeElementSet {
  private final List<LineNumberedException> exceptionList;

  private final List<InstructionElement> instructionList;

  private final Map<String, Integer> labelMap;

  private final Map<String, String> ruleMap;

  CodeElementSet(List<LineNumberedException> exceptionList,
                 List<InstructionElement> instructionList,
                 Map<String, Integer> labelMap,
                 Map<String, String> ruleMap) {
    this.exceptionList = exceptionList;

    this.instructionList = instructionList;

    this.labelMap = labelMap;

    this.ruleMap = ruleMap;
  }

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

  public boolean isExceptionOccurred() {
    return exceptionList.size() > 0;
  }

  public List<LineNumberedException> getExceptionList() {
    return exceptionList;
  }

  public List<InstructionElement> getInstructionList() {
    return instructionList;
  }

  public Map<String, Integer> getLabelMap() {
    return labelMap;
  }

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
