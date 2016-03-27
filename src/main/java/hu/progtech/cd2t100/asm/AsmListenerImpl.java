package hu.progtech.cd2t100.asm;

import org.antlr.v4.runtime.misc.NotNull;

class AsmListenerImpl extends AsmBaseListener {
  @Override
  public void exitPreprocessorRule(@NotNull AsmParser.PreprocessorRuleContext ctx) {
    System.err.println(ctx.ruleName().getText());

    System.err.println(ctx.argument().getText());
  }
}
