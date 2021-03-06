package hu.progtech.cd2t100.asm;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.ArgumentType;

/**
 * The real workhorse class of analyzing the AST produced by ANTLR and spotting
 * various input errors. As the AST gets processed, it extracts the data
 * from the ANTLR rule contexts and makes it available for further processing.
 * The class is able to perceive different semantical error types to prevent erroneus input
 * from reaching the next processing stage. These error types are collected using
 * the following exceptions:
 * <ul>
 *  <li>{@link DuplicateLabelNameException}</li>
 *  <li>{@link LabelNameCollisionException}</li>
 *  <li>{@link UnknownArgumentTypeException}</li>
 * </ul>
 * This class is only used by the {@link CodeFactory}.
 */
class AsmListenerImpl extends AsmBaseListener {
  private static final Logger	logger = LoggerFactory.getLogger(AsmListenerImpl.class);

  private List<LineNumberedException> exceptionList;

  private List<InstructionElement> instructionList;

  private Map<String, Integer> labelMap;

  private Map<String, String> ruleMap;

  private Set<String> registerNameSet;

  private Set<String> portNameSet;

  /**
   * Constructs a new listener with the given register and port set. Referencing
   * other registers and/or ports than these ones results an appropiate exception.
   *
   * @param registerNameSet a set containing the usable register names
   * @param portNameSet a set containing the usable existing port names
   */
  public AsmListenerImpl(Set<String> registerNameSet,
                         Set<String> portNameSet) {
    this.registerNameSet = registerNameSet;
    this.portNameSet = portNameSet;

    this.exceptionList = new ArrayList<>();
    this.instructionList = new ArrayList<>();
    this.labelMap = new HashMap<>();
    this.ruleMap = new HashMap<>();
  }

  @Override
  public void exitPreprocessorRule(AsmParser.PreprocessorRuleContext ctx) {
    Optional<AsmParser.ArgumentContext> arg = Optional.ofNullable(ctx.argument());

    ruleMap.put(ctx.ruleName().getText(),
                arg.isPresent() ? arg.get().getText() : "");

    logger.debug("Rule encountered: {}", ctx.ruleName().getText());
  }

  @Override
  public void exitLabel(AsmParser.LabelContext ctx) {
    if (!(ctx.getParent() instanceof AsmParser.InstructionContext)) {
      try {
        addLabel(ctx, false);
      } catch (LineNumberedException e) {
        exceptionList.add(e);
      }
    }
  }

  @Override
  public void exitInstruction(AsmParser.InstructionContext ctx) {
    addInstruction(ctx);

    if (ctx.label() != null) {
      try {
        addLabel(ctx.label(), true);
      } catch (LineNumberedException e) {
        exceptionList.add(e);
      }
    }

    updateUnsetLabels(instructionList.size() - 1);
  }

  @Override
  public void exitProgram(AsmParser.ProgramContext ctx) {
    for (InstructionElement elem : instructionList) {
      ArgumentElement[] args = elem.getArgumentElements();

      for (int i = 0; i < args.length; ++i) {
        try {
          args[i] = evaluateArgumentType(args[i]);
        } catch (UnknownArgumentTypeException e) {
          exceptionList.add(e);
        }
      }
    }

    updateUnsetLabels(0);

    logger.debug("Parsing is done.");
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

  /**
   * Extracts the underlying label from the specified {@code AsmParser.LabelContext}
   * then adds it to the {code labelMap} if the label was unique and non-colliding.
   * Also checks if the extracted label name collides with a register or
   * port name or if its duplicated in the processed source code.
   *
   * The value of {@code isPositionKnown} must be {@code false} for example
   * when processing the AST of the following code:
   *  <pre>
   * {@code
   *  start:
   *    MOV 10
   * }
   *  </pre>
   *
   * @param ctx the context that contains the label to be extracted
   * @param isPositionKnown whether the position of the instruction that follows
   *                        label is known
   *
   * @throws DuplicateLabelNameException if the underlying label of {@code ctx}
   *                                     already exists
   * @throws LabelNameCollisionException if the underlying label of {@code ctx}
   *                                     collides with either a register or a
   *                                     port name
   */
  private void addLabel(AsmParser.LabelContext ctx, boolean isPositionKnown)
    throws DuplicateLabelNameException, LabelNameCollisionException {

    String name = StringUtils.chop(ctx.getText());

    if (labelMap.containsKey(name)) {
      throw new DuplicateLabelNameException(Location.fromParserRuleContext(ctx),
                                            name);
    } else if ((portNameSet.contains(name)) || (registerNameSet.contains(name))) {
      throw new LabelNameCollisionException(Location.fromParserRuleContext(ctx),
                                            name);
    }

    labelMap.put(name, isPositionKnown ? instructionList.size() - 1 : -1);

    logger.debug("Label parsed: {}", name);
  }

  /**
   * Extracts the underlying instruction from the specified {@code AsmParser.InstructionContext}
   * and then adds a new {@code InstructionElement} representing the extracted instruction.
   * Since the {@code InstructionElement} must contain {@code ArgumentElement}s
   * corresponding to its arguments, the method also inspects the
   * {@code ArgumentContext}s of {@code ctx}.
   *
   * @param ctx the context that contains the instruction to be extracted
   *
   * @see ArgumentElement
   * @see InstructionElement
   */
  private void addInstruction(AsmParser.InstructionContext ctx) {
    String opcode = ctx.opcode().getText();

    ArgumentElement[] args =
      ctx.argument().stream()
          .map(arg -> {
            return new ArgumentElement(Location.fromParserRuleContext(arg),
                                       arg.getText());
          })
          .toArray(ArgumentElement[]::new);

    instructionList.add(new InstructionElement(
                          Location.fromParserRuleContext(ctx.opcode()),
                          opcode, args));

    logger.debug("Instruction parsed: {}", opcode);
  }

  /**
   * Evaluates the argument type of the specified {@code ArgumentElement}.
   * The type of the {@code ArgumentElement}s may not be evaluated right at
   * the creation of an {@code InstructionElement}. Such situation occurs
   * when a label is forward-referenced, just like this:
   *  <pre>
   * {@code
   *  JMP zero
   *  # additional instructions
   *  zero: NEG
   * }
   *  </pre>
   *
   * @param arg the {@code ArgumentElement} to be re-evaluated
   *
   * @return the new {@code ArgumentElement} with the same type as {@code arg}
   *         but with the evaluated type
   *
   * @throws UnknownArgumentTypeException If the type of the argument cannot be
   *                                      be determined. For example, when it
   *                                      references an unexistant register or port.
   *
   * @see ArgumentType
   * @see ArgumentElement
   */
  private ArgumentElement evaluateArgumentType(ArgumentElement arg)
    throws UnknownArgumentTypeException {
    ArgumentType argType = ArgumentType.NOT_EVALUATED;

    String argValue = arg.getValue();

    if (portNameSet.contains(argValue)) {
      argType = ArgumentType.PORT;
    } else if (registerNameSet.contains(argValue)) {
      argType = ArgumentType.REGISTER;
    } else if (labelMap.keySet().contains(argValue)) {
      argType = ArgumentType.LABEL;
    } else {
      try {
        Integer.parseInt(argValue);

        argType = ArgumentType.NUMBER;
      } catch (NumberFormatException e ) {
        throw new UnknownArgumentTypeException(arg.getLocation(),
                                               argValue);
      }
    }

    logger.trace("Argument with type {} and value {} evaluated.", argType, argValue);

    return new ArgumentElement(arg.getLocation(),
                               argValue, argType);
  }

  /**
   *  Sets the mapped values of labels mapped to -1 to the given {@code value}.
   *  This is necessary, because not-yet referenced labels are mapped to
   *  -1 in the {@code labelMap}. Multiple unreferenced labels may exist
   *  when parsing the AST produced by the following code snippet:
   *  <pre>
   *  {@code
   *    start1:
   *    start2:
   *    start3:
   *      MOV LEFT UP
   *  }
   *  </pre>
   *
   *  @param value the new mapped value of the entries with previous value -1
   */
  private void updateUnsetLabels(Integer value) {
    for (String key : labelMap.keySet()) {
      if (labelMap.get(key) == -1) {
        labelMap.put(key, value);
      }
    }
  }
}
