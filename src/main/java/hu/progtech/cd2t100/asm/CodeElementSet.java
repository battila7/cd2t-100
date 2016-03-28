package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;

public class CodeElementSet {
  List<LineNumberedException> exceptionList;

  private CodeElementSet(List<LineNumberedException> exceptionList) {
    this.exceptionList = exceptionList;
  }

  public static CodeElementSet fromListeners(AsmListenerImpl listener,
                                             AsmErrorListener errorListener) {
    List<LineNumberedException> lst = new ArrayList<>();

    lst.addAll(listener.getExceptionList());
    lst.addAll(errorListener.getExceptionList());

    return new CodeElementSet(lst);
  }

  public boolean isExceptionOccurred() {
    return exceptionList.size() > 0;
  }

  public List<LineNumberedException> getExceptionList() {
    return exceptionList;
  }
}
