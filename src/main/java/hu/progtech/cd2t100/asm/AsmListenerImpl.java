package hu.progtech.cd2t100.asm;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import hu.progtech.cd2t100.computation.ArgumentType;

class AsmListenerImpl extends AsmBaseListener {
  private List<LineNumberedException> exceptionList;

  private List<InstructionElement> instructionList;

  private Map<String, Integer> labelMap;

  private Map<String, String> ruleMap;

  private Set<String> registerNameSet;

  private Set<String> portNameSet;

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
    if (ctx.label() != null) {
      try {
        addLabel(ctx.label(), false);
      } catch (LineNumberedException e) {
        exceptionList.add(e);
      }
    }

    addInstruction(ctx);

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
  }

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
                          Location.fromParserRuleContext(ctx),
                          opcode, args));
  }

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

    return new ArgumentElement(arg.getLocation(),
                               argValue, argType);
  }

  private void updateUnsetLabels(Integer value) {
    for (String key : labelMap.keySet()) {
      if (labelMap.get(key) == -1) {
        labelMap.put(key, value);
      }
    }
  }
}
