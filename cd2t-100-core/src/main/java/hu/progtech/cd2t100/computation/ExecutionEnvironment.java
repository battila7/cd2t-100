package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.Optional;

/**
 *  This class makes it possible for Groovy instruction classes to safely
 *  operate on {@code Node} objects.
 */
public class ExecutionEnvironment {
  private final Node node;

  private final Map<String, String> rules;

  /**
   *  Constructs a new {@code ExecutionEnvironment} based on the specified {@code Node}.
   *
   *  @param node the {@code Node} object to be hidden behind
   *              the new {@code ExecutionEnvironment} instance
   *  @param rules the map of preprocessor rules
   */
  public ExecutionEnvironment(Node node, Map<String, String> rules) {
    this.node = node;

    this.rules = rules;
  }

  /**
   *  Instructs the underlying node to jump to the instruction
   *  after the specified label. If the parameter is not an existing label
   *  then nothing happens. Note that jumps only happen in the next cycle.
   *
   *  @param label the label to jump after
   */
  public void jumpToLabel(String label) {
    Map<String, Integer> labelMap = node.getLabels();

    if (labelMap != null) {
      Optional.ofNullable(labelMap.get(label))
              .ifPresent(x -> node.setNextInstruction(x));
    }
  }

  /**
   *  Instructs the underlying node to jump to the specified position.
   *  If an invalid position is passed, nothing happens.
   *  Note that jumps only happen in the next cycle.
   *
   *  @param position the position to jump to
   */
  public void jumpAbsolute(int position) {
    node.setNextInstruction(position);
  }

  /**
   *  Instructs the underlying node to jump to the specified position
   *  relative to the current instruction pointer value.
   *  If an invalid position is passed, nothing happens.
   *  Note that jumps only happen in the next cycle.
   *
   *  @param position the position relative to the current position.
   */
  public void jumpRelative(int position) {
    int iptr = node.getInstructionPointer();

    node.setNextInstruction(iptr + position);
  }

  /**
   *  Gets the value associated with the specified rule name.
   *
   *  @param ruleName the rule's name
   *
   *  @return the value of the specified rule
   */
  public String getRuleValue(String ruleName) {
    return rules.get(ruleName);
  }
}
