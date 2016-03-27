package hu.progtech.cd2t100.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;

import hu.progtech.cd2t100.computation.Argument;
import hu.progtech.cd2t100.computation.ArgumentType;

class AsmListenerImpl extends AsmBaseListener {
  private List<InstructionElement> instructionList;

  private Map<String, Integer> labelMap;

  private Map<String, String> ruleMap;

  private Set<String> portNameSet;

  public AsmListenerImpl(Set<String> portNameSet,
                         Map<String, String> ruleMap,
                         Map<String, Integer> labelMap,
                         List<InstructionElement> instructionList) {
    this.portNameSet = portNameSet;
    this.labelMap = labelMap;
    this.ruleMap = ruleMap;
    this.instructionList = instructionList;
  }

  @Override
  public void exitPreprocessorRule(@NotNull AsmParser.PreprocessorRuleContext ctx) {
    ruleMap.put(ctx.ruleName().getText(), ctx.argument().getText());
  }

  @Override
  public void exitLabel(@NotNull AsmParser.LabelContext ctx) {
    if (!(ctx.getParent() instanceof AsmParser.InstructionContext)) {
      addLabel(ctx.getText(), false);
    }
  }

  @Override
  public void exitInstruction(@NotNull AsmParser.InstructionContext ctx) {
    if (ctx.label() != null) {
      addLabel(ctx.label().getText(), true);
    }

    addInstruction(ctx);

    updateUnsetLabels(instructionList.size() - 1);
  }

  @Override
  public void exitProgram(@NotNull AsmParser.ProgramContext ctx) {
    for (InstructionElement elem : instructionList) {
      System.err.println(elem);
    }

    updateUnsetLabels(0);
  }

  private void addLabel(String name, boolean isPositionKnown) {
    if (labelMap.containsKey(name)) {
      // throw new Exception("Duplicate label name: " + name);
    } else if (portNameSet.contains(name)) {
      // throw new Exception("Port and label name collision: " + name);
    }

    labelMap.put(name, isPositionKnown ? instructionList.size() - 1 : -1);
  }

  private void addInstruction(AsmParser.InstructionContext ctx) {
    int lineNumber = ctx.getStart().getLine();

    String opcode = ctx.opcode().getText();

    Argument[] args =
      ctx.argument().stream()
          .map(arg -> new Argument(arg.getText(), ArgumentType.NOT_EVALUATED))
          .toArray(Argument[]::new);

    instructionList.add(new InstructionElement(lineNumber, opcode, args));
  }

  private void updateUnsetLabels(Integer value) {
    for (String key : labelMap.keySet()) {
      if (labelMap.get(key) == -1) {
        labelMap.put(key, value);
      }
    }
  }
}
