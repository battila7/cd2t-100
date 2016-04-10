package hu.progtech.cd2t100.computation;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import hu.progtech.cd2t100.asm.ArgumentElement;
import hu.progtech.cd2t100.asm.InstructionElement;

import hu.progtech.cd2t100.formal.ParameterType;
import hu.progtech.cd2t100.formal.InstructionInfo;
import hu.progtech.cd2t100.formal.FormalParameter;
import hu.progtech.cd2t100.formal.FormalCall;

final class ArgumentMatcher {
  private final Set<String> registerSet;
	private final Set<String> readablePortSet;
	private final Set<String> writeablePortSet;

  private Set<String> labelSet;

  private InstructionElement element;

  private InstructionInfo info;

  private List<FormalCall> possibleCalls;

  private ArgumentElement[] suppliedArguments;

  private ArrayList<Argument> actualArguments;

  private FormalCall matchedCall;

  ArgumentMatcher(Set<String> registerSet,
                  Set<String> readablePortSet,
                  Set<String> writeablePortSet)
  {
    this.registerSet = registerSet;
		this.readablePortSet = readablePortSet;
		this.writeablePortSet = writeablePortSet;
  }

  void setInstructionElement(InstructionElement element) {
    this.element = element;

    suppliedArguments = element.getArgumentElements();
  }

  void setInstructionInfo(InstructionInfo info) {
    possibleCalls = info.getPossibleCalls();
  }

  void setLabels(Set<String> labelSet) {
    this.labelSet = labelSet;
  }

  void match() throws ArgumentMatchingException {
    actualArguments = new ArrayList<>();

    for (int i = 0; i < suppliedArguments.length; ++i) {
      matchOne(i);

      if (possibleCalls.isEmpty()) {
        throw new ArgumentMatchingException(
          suppliedArguments[i].getLocation(),
          "No suitable instruction overload found");
      }
    }

    if (possibleCalls.size() > 1) {
      throw new ArgumentMatchingException(
        element.getLocation(),
        "Ambiguous argument list, could not determine matching instruction overload.");
    }

    matchedCall = possibleCalls.get(0);

    for (FormalParameter formalParam : matchedCall.getFormalParameterList()) {
      if (formalParam.hasImplicitValue() &&
          (!checkImplicitParameter(formalParam)))
      {
        throw new ArgumentMatchingException(
          element.getLocation(),
          "Implicit parameter error. Is this instruction compatible with the node?");
      }
    }
  }

  List<Argument> getActualArguments() {
    return actualArguments;
  }

  FormalCall getMatchedCall() {
    return matchedCall;
  }

  private void matchOne(int argIndex) {
    ArgumentElement argElement = suppliedArguments[argIndex];

    for (Iterator<FormalCall> iterator = possibleCalls.iterator();
         iterator.hasNext(); )
    {
      FormalCall formalCall = iterator.next();

      FormalParameter formalParam =
        formalCall.getFormalParameterList().get(argIndex);

      if (!suppliedAndFormalMatches(argElement, formalParam)) {
        iterator.remove();
      }

      actualArguments.add(new Argument(argElement.getValue(),
                                       formalParam.getParameterType()));
    }
  }

  private boolean suppliedAndFormalMatches(ArgumentElement argElement,
                                           FormalParameter formalParam)
  {
    ArgumentType argType    = argElement.getArgumentType();
    ParameterType paramType = formalParam.getParameterType();

    switch (argType) {
      case NUMBER:
        return paramType == ParameterType.NUMBER;
      case LABEL:
        return paramType == ParameterType.LABEL;
      case REGISTER:
        return paramType == ParameterType.REGISTER;
      case PORT:
        if (paramType == ParameterType.READ_PORT) {
          return readablePortSet.contains(argElement.getValue());
        } else {
          return writeablePortSet.contains(argElement.getValue());
        }
      default:
        return false;
    }
  }

  private boolean checkImplicitParameter(FormalParameter formalParam) {
    String value = formalParam.getImplicitValue();

    switch (formalParam.getParameterType()) {
      case NUMBER:
        try {
          Integer.parseInt(value);

          return true;
        } catch (NumberFormatException nfe) {
          return false;
        }
      case LABEL:
        return labelSet.contains(value);
      case REGISTER:
        return registerSet.contains(value);
      case READ_PORT:
        return readablePortSet.contains(value);
      case WRITE_PORT:
        return writeablePortSet.contains(value);
      default:
        return false;
    }
  }
}
