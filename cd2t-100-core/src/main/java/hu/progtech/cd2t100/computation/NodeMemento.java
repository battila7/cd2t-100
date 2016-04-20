package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.Set;
import java.util.Arrays;

/**
 *  Stores the state of a {@code Node} object. Used to externalize and save the fields
 *  of a node. Note that the actual {@code Instructions} objects are not contained within
 *  the {@code NodeMemento}, only the source code. Although this makes harder to restore
 *  a node, it makes this object more lightweight. This class should be used to get
 *  runtime data of the nodes instead of restoring them into a previous state although that's
 *  possible too as stated.
 *
 *  {@code NodeMemento} can be safely passed around between threads since it holds no references
 *  to its originator (the {@code Node}). But the inner maps should be used carefully because
 *  they are mutable.
 */
public class NodeMemento {
  private final Map<String, int[]> registerValues;

  private final Set<String> portNameSet;

  private final String sourceCode;

  private final int instructionPointer;

  private final ExecutionState executionState;

  private final int currentLine;

  private final String globalName;

  /**
   *  Constructs a new {@code NodeMemento} object storing the specified values.
   *
   *  @param registerValues a map mapping register names to their contents
   *  @param portNameSet the set of port names
   *  @param sourceCode the source code of the originator {@code Node}
   *  @param instructionPointer the value of the instruction pointer
   *  @param executionState the execution state of the originator {@code Node}
   *  @param currentLine the currently executed source code line
   *  @param globalName the global name of the originator
   */
  public NodeMemento(Map<String, int[]> registerValues,
                     Set<String> portNameSet,
                     String sourceCode,
                     int instructionPointer,
                     ExecutionState executionState,
                     int currentLine,
                     String globalName)
  {
    this.registerValues = registerValues;

    this.portNameSet = portNameSet;

    this.sourceCode = sourceCode;

    this.instructionPointer = instructionPointer;

    this.executionState = executionState;

    this.currentLine = currentLine;

    this.globalName = globalName;
  }

  /**
   *  Gets the register names along with their values.
   *
   *  @return a map with register names and values
   */
  public Map<String, int[]> getRegisterValues() {
    return registerValues;
  }

  /**
   *  Gets the port names.
   *
   *  @return the port name set
   */
  public Set<String> getPortNameSet() {
    return portNameSet;
  }

  /**
   *  Gets the source code.
   *
   *  @return the source code
   */
  public String getSourceCode() {
    return sourceCode;
  }

  /**
   *  Gets the instruction pointer.
   *
   *  @return the instruction pointer
   */
  public int getInstructionPointer() {
    return instructionPointer;
  }

  /**
   *  Gets the execution state.
   *
   *  @return the execution state
   */
  public ExecutionState getExecutionState() {
    return executionState;
  }

  /**
   *  Gets the current line.
   *
   *  @return the current line
   */
  public int getCurrentLine() {
    return currentLine;
  }

  /**
   *  Gets the global name.
   *
   *  @return the global name
   */
  public String getGlobalName() {
    return globalName;
  }

  @Override
  public String toString() {
    String rep = globalName + "(" + executionState + ") @l: " + currentLine + "\n";

    rep += "IP: " + instructionPointer + "\n";

    for (Map.Entry<String, int[]> entry : registerValues.entrySet()) {
      rep += "\t" + entry.getKey() + " = " + Arrays.toString(entry.getValue()) + "\n";
    }

    return rep;
  }
}
