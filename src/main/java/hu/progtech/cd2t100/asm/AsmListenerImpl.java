package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import org.javatuples.Pair;

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
    ruleMap.put(ctx.ruleName().getText(), ctx.argument().getText());
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

  private void addLabel(AsmParser.LabelContext ctx, boolean isPositionKnown)
    throws DuplicateLabelNameException, LabelNameCollisionException {
    Pair<Integer, Integer> pos = getLinePosition(ctx);

    String name = ctx.getText();

    if (labelMap.containsKey(name)) {
      throw new DuplicateLabelNameException(pos.getValue0(),
                                            pos.getValue1(),
                                            name);
    } else if (portNameSet.contains(name)) {
      throw new LabelNameCollisionException(pos.getValue0(),
                                            pos.getValue1(),
                                            name);
    }

    labelMap.put(name, isPositionKnown ? instructionList.size() - 1 : -1);
  }

  private void addInstruction(AsmParser.InstructionContext ctx) {
    Pair<Integer, Integer> pos = getLinePosition(ctx);

    String opcode = ctx.opcode().getText();

    ArgumentElement[] args =
      ctx.argument().stream()
          .map(arg -> {
            Pair<Integer, Integer> argPos = getLinePosition(arg);

            return new ArgumentElement(argPos.getValue0(),
                                       argPos.getValue1(),
                                       arg.getText());
          })
          .toArray(ArgumentElement[]::new);

    instructionList.add(new InstructionElement(
                          pos.getValue0(), pos.getValue1(),
                          opcode, args));
  }

  private Pair<Integer, Integer> getLinePosition(ParserRuleContext ctx) {
    Token start = ctx.getStart();

    return new Pair<>(start.getLine(), start.getCharPositionInLine());
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
        throw new UnknownArgumentTypeException(arg.getLineNumber(),
                                               arg.getColumnNumber(),
                                               argValue);
      }
    }

    return new ArgumentElement(arg.getLineNumber(),
                               arg.getColumnNumber(),
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
