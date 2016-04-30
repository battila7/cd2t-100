package hu.progtech.cd2t100.computation;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.asm.ArgumentElement;
import hu.progtech.cd2t100.asm.InstructionElement;

import hu.progtech.cd2t100.formal.ParameterType;
import hu.progtech.cd2t100.formal.InstructionInfo;
import hu.progtech.cd2t100.formal.FormalParameter;
import hu.progtech.cd2t100.formal.FormalCall;

/**
 *  The class responsible for matching the actual arguments
 *  with the formal parameters and choosing the correct
 *  {@code apply} method overload. {@code ArgumentMatcher} pairs up
 *  an {@code InstructionElement} with a {@link hu.progtech.cd2t100.formal.FormalCall}
 *  that can be executed on a node. An instance is created for each node by the
 *  node's {link InstructionFactory}.
 *
 *  Matching cannot be done without node-specific knowledge
 *  (registers, readable/writeable ports), so {@code ArgumentMatcher}
 *  can only be instantiated with the correct node configuration.
 */
final class ArgumentMatcher {
  public static final Logger logger =
    LoggerFactory.getLogger(ArgumentMatcher.class);

  private final Set<String> registerSet;
	private final Set<String> readablePortSet;
	private final Set<String> writeablePortSet;

  private Set<String> labelSet;

  private InstructionElement element;

  private List<FormalCall> possibleCalls;

  private ArgumentElement[] suppliedArguments;

  private ArrayList<Argument> actualArguments;

  private FormalCall matchedCall;

  /**
   *  Constructs a new {@code ArgumentMatcher} with the
   *  specified node configuration (register and port names).
   *
   *  @param registerSet the register set of the node
   *  @param readablePortSet readable ports of the node
   *  @param writeablePortSet writeable ports of the node
   */
  public ArgumentMatcher(Set<String> registerSet,
                  Set<String> readablePortSet,
                  Set<String> writeablePortSet)
  {
    this.registerSet = registerSet;
		this.readablePortSet = readablePortSet;
		this.writeablePortSet = writeablePortSet;
  }

  /**
   *  Sets the {@code InstructionElement} to be matched.
   *
   *  @param element the element to be matched
   */
  public void setInstructionElement(InstructionElement element) {
    this.element = element;

    suppliedArguments = element.getArgumentElements();
  }

  /**
   *  Sets the {@code InstructionInfo} that contains the
   *  {@link FormalCall}s to match against.
   *
   *  @param info the instruction info
   */
  public void setInstructionInfo(InstructionInfo info) {
    possibleCalls = info.getPossibleCalls();
  }

  /**
   *  Sets the label set that can be used during matching.
   *  Because there's only one instance created per node, the labels
   *  cannot be passed to the constructor.
   *
   *  @param labelSet the labels extracted from the source code
   */
  public void setLabels(Set<String> labelSet) {
    this.labelSet = labelSet;
  }

  /**
   *  Does the actual matching after the class the
   *  instruction element and instruction info have been set.
   *  If the matching was successful, the chosen {@code FormalCall} and
   *  actual arguments can be retrieved with the corresponding getter methods.
   *  If the matching fails, an exception is thrown.
   *
   *  @throws ArgumentMatchingException If there were no matching {code FormalCall}.
   *
   *  @see hu.progtech.cd2t100.formal.FormalCall
   *  @see Argument
   */
  public void match() throws ArgumentMatchingException {
    actualArguments = new ArrayList<>();

    possibleCalls = possibleCalls
                      .stream()
                      .filter(x -> x.getDemandedParams() == suppliedArguments.length)
                      .collect(Collectors.toList());

    if (possibleCalls.isEmpty()) {
      throw new ArgumentMatchingException(
        element.getLocation(),
        "No suitable instruction overload found.");
    }

    for (int i = 0; i < suppliedArguments.length; ++i) {
      ParameterType matchedType = matchOne(i);

      logger.trace("Value: {}", suppliedArguments[i].getValue());
      logger.trace("Type: {}", matchedType);

      if (matchedType == null) {
        throw new ArgumentMatchingException(
          suppliedArguments[i].getLocation(),
          "No suitable instruction overload found.");
      }

      actualArguments.add(new Argument(suppliedArguments[i].getValue(),
                                       matchedType));
    }

    if (possibleCalls.size() > 1) {
      throw new ArgumentMatchingException(
        element.getLocation(),
        "Ambiguous argument list, could not determine matching instruction overload.");
    }

    matchedCall = possibleCalls.get(0);

    logger.trace("Call: {}", matchedCall);

    for (FormalParameter formalParam : matchedCall.getFormalParameterList()) {
      if (formalParam.hasImplicitValue()) {
        if (!checkImplicitParameter(formalParam)) {
          throw new ArgumentMatchingException(
            element.getLocation(),
            "Implicit parameter error. Is this instruction compatible with the node?");
        }

        actualArguments.add(new Argument(formalParam.getImplicitValue(),
                                         formalParam.getParameterType()));
      }
    }
  }

  /**
   *  Gets the list of the actual arguments. If the {@code match} method was not
   *  successful the list may not contain all arguments.
   *
   *  @return the list of actual arguments
   */
  public List<Argument> getActualArguments() {
    return actualArguments;
  }

  /**
   *  Gets the matched {@code FormalCall}. If the {@code match} method was not
   *  succesful, the method returns {@code null}.
   *
   *  @return the matched call or {@code null} if an exception was thrown
   *          in the {@code match} method.
   */
  public FormalCall getMatchedCall() {
    return matchedCall;
  }

  private ParameterType matchOne(int argIndex) {
    ArgumentElement argElement = suppliedArguments[argIndex];

    ParameterType matchedType = null;

    for (Iterator<FormalCall> iterator = possibleCalls.iterator();
         iterator.hasNext(); )
    {
      FormalCall formalCall = iterator.next();

      FormalParameter formalParam =
        formalCall.getFormalParameterList().get(argIndex);

     logger.trace("Formal Param: {}", formalParam);

      if (!suppliedAndFormalMatches(argElement, formalParam)) {
        logger.trace("Match failed");

        iterator.remove();
      } else {
        matchedType = formalParam.getParameterType();

        logger.trace("Matched {}", matchedType);
      }
    }

    return matchedType;
  }

  private boolean suppliedAndFormalMatches(ArgumentElement argElement,
                                           FormalParameter formalParam)
  {
    ArgumentType argType    = argElement.getArgumentType();
    ParameterType paramType = formalParam.getParameterType();

    logger.trace("ArgType {} paramType {}", argType, paramType);

    switch (argType) {
      case NUMBER:
        return paramType.equals(ParameterType.NUMBER);
      case LABEL:
        return paramType.equals(ParameterType.LABEL);
      case REGISTER:
        return paramType.equals(ParameterType.REGISTER);
      case PORT:
        logger.trace("Entered PORT");

        if (paramType.equals(ParameterType.READ_PORT)) {
          logger.trace("Readables: {}", readablePortSet);

          return readablePortSet.contains(argElement.getValue());
        } else if (paramType.equals(ParameterType.WRITE_PORT)) {
          return writeablePortSet.contains(argElement.getValue());
        }

        return false;
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
