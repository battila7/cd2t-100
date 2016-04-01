package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

final class ExpectedElementSet {
  private final ArrayList<ExceptionDescriptor> exceptionList;

  private final ArrayList<InstructionElement> instructionList;

  private final HashMap<String, Integer> labelMap;

  private final HashMap<String, String> ruleMap;

  public ExpectedElementSet(ArrayList<ExceptionDescriptor> exceptionList,
                            ArrayList<InstructionElement> instructionList,
                            HashMap<String, Integer> labelMap,
                            HashMap<String, String> ruleMap) {
    this.exceptionList = exceptionList;

    this.instructionList = instructionList;

    this.labelMap = labelMap;

    this.ruleMap = ruleMap;
  }

  public boolean compareToElementSet(CodeElementSet elementSet) {
    if (!compareExceptionList(elementSet.getExceptionList())) {
      return false;
    }

    if (!instructionList.equals(elementSet.getInstructionList())) {
      return false;
    }

    if (!labelMap.equals(elementSet.getLabelMap())) {
      return false;
    }

    if (!ruleMap.equals(elementSet.getRuleMap())) {
      return false;
    }

    return true;
  }

  private boolean compareExceptionList(List<LineNumberedException> lst) {
    for (int i = 0; i < lst.size(); ++i) {
      ExceptionDescriptor d = exceptionList.get(i);
      LineNumberedException e = lst.get(i);

      if (!(e.getClass().getSimpleName() != d.getName())) {
        return false;
      }

      if (!(e.getLocation().equals(d.getLocation()))) {
        return false;
      }
    }

    return true;
  }
}
