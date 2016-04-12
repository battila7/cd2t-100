package hu.progtech.cd2t100.formal;

import java.util.List;

class ExpectedInstructionInfo {
  private final String opcode;

  private final List<String> usedPreprocessorRules;

  private final List<FormalCall> possibleCalls;

  private final String thrownExceptionName;

  private final String messageFragment;

  public ExpectedInstructionInfo(String opcode,
                                 List<String> usedRules,
                                 List<FormalCall> possibleCalls,
                                 String thrownExceptionName,
                                 String messageFragment) {
    this.opcode = opcode;

    this.usedPreprocessorRules = usedRules;

    this.possibleCalls = possibleCalls;

    this.thrownExceptionName = thrownExceptionName;

    this.messageFragment = messageFragment;
  }

  public String getOpcode() {
    return opcode;
  }

  public List<String> getUsedPreprocessorRules() {
    return usedPreprocessorRules;
  }

  public List<FormalCall> getPossibleCalls() {
    return possibleCalls;
  }

  public String getThrownExceptionName() {
    return thrownExceptionName;
  }

  public String getMessageFragment() {
    return messageFragment;
  }

  public boolean compareToInstructionInfo(InstructionInfo info) {
    if (!(info.getOpcode().equals(opcode))) {
      return false;
    }

    if (!(info.getUsedPreprocessorRules().equals(usedPreprocessorRules))) {
      return false;
    }

    List<FormalCall> infoCalls = info.getPossibleCalls();

    if (infoCalls.size() != possibleCalls.size()) {
      return false;
    }

    for (int i = 0; i < possibleCalls.size(); ++i) {
      FormalCall ownCall = possibleCalls.get(i);

      boolean hasMatchingCall = false;

      /*
       *  The array returned by getDeclaredMethods() is not in any particular order,
       *  therefore we cannot simply use List.equals().
       */
      for (int j = 0; j < infoCalls.size(); ++j)
      {
        FormalCall thatCall = possibleCalls.get(j);

        if (ownCall.getFormalParameterList().equals(thatCall.getFormalParameterList())) {
          hasMatchingCall = true;

          break;
        }
      }

      if (!hasMatchingCall) {
        return false;
      }
    }

    return true;
  }
}
