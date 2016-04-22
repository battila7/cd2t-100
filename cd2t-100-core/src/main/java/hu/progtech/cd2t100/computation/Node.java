package hu.progtech.cd2t100.computation;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;
import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

import hu.progtech.cd2t100.formal.ReadResult;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 *  Represents a processor node that's able to execute instructions. {@code Node}s
 *  can communicate with each other through {@link CommunicationPort}s and store
 *  data in {@link Register}s.
 *
 *  It's guaranteed that every {@code Node} has at least two registers,
 *  {@code ACC} and {@code BAK} to ensure compatibility with {@code TIS-100}.'
 */
public class Node {
  private static Pattern newlinePattern =
    Pattern.compile("\r\n|\r|\n");

  private final String globalName;

  private final Map<String, Register> registerMap;
  private final Map<String, CommunicationPort> readablePortMap;
  private final Map<String, CommunicationPort> writeablePortMap;

  private final HashSet<String> portNameSet;
  private final HashSet<CommunicationPort> blockedWriteablePorts;

  private final int maximumSourceCodeLines;

  private final InstructionRegistry instructionRegistry;

  private String sourceCode;

  private boolean readyToRun;

  private List<Instruction> instructions;

  private int instructionPointer;

  private int nextInstruction;

  private ExecutionEnvironment execEnv;

  private CodeElementSet codeElementSet;

  private ExecutionState executionState;

  /**
   *  Constructs a new {@code Node} using the specified
   *  registry and other properties.
   *
   *  @param instructionRegistry the {@code InstructionRegistry}
   *                             the new {@code Node} will be using
   *  @param maximumSourceCodeLines the maximum length of source code
   *                                in lines this {@code Node} can store
   *  @param globalName the global name of the {@code Node}
   *  @param registerMap the map of registers
   *  @param readablePortMap the map of readable ports
   *  @param writeablePortMap the map of writeable ports
   */
  public Node(InstructionRegistry instructionRegistry,
              int maximumSourceCodeLines,
              String globalName,
              Map<String, Register> registerMap,
              Map<String, CommunicationPort> readablePortMap,
              Map<String, CommunicationPort> writeablePortMap)

  {
    this.instructionRegistry = instructionRegistry;

    this.maximumSourceCodeLines = maximumSourceCodeLines;

    this.registerMap = registerMap;

    this.readablePortMap = readablePortMap;

    this.writeablePortMap = writeablePortMap;

    this.portNameSet = new HashSet<>(readablePortMap.keySet());

    this.blockedWriteablePorts = new HashSet<>();

    this.globalName = globalName;

    portNameSet.addAll(writeablePortMap.keySet());

    execEnv = new ExecutionEnvironment(this, instructionRegistry.getRules());

    executionState = ExecutionState.IDLE;

    sourceCode = "";

    readyToRun = false;
  }

  /**
   *  Instructs the {@code Node} to execute its next step. This
   *  corresponds to one processor cycle.
   *
   *  The instruction pointer's value determines the executed instruction.
   *  The instruction can be executed if and only if all of its read dependencies
   *  are fulfilled and no writeable port contains data.
   *
   *  @throws IllegalStateException If there were errors during the instruction
   *                                 generation.
   */
  public void step() throws IllegalStateException {
    if (!readyToRun) {
      throw new IllegalStateException(
        "Node's not able to run because of previous errors.");
    }

    if (instructions.isEmpty()) {
      return;
    }

    if (!blockedWriteablePorts.isEmpty()) {
      return;
    }

    Instruction currentInstruction = instructions.get(instructionPointer);

    if (!readDependenciesFulfilled(currentInstruction)) {
      executionState = ExecutionState.IDLE;

      return;
    }

    Invoker invoker = new Invoker(this, currentInstruction);

    invoker.invoke();

    instructionPointer = nextInstruction;
  }

  /**
   *  Gets the global name.
   *
   *  @return the global name
   */
  public String getGlobalName() {
    return globalName;
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
   *  Sets the next value of the instruction pointer after executing the
   *  current instruction. It the parameter is below 0 then it will be
   *  set to 0. Similarly, if it's above the number of instructions it
   *  will be set to the index of last instruction.
   *
   *  @param i the next value of the instruction pointer
   */
  public void setNextInstruction(int i) {
    if ((i < 0) || (i > instructions.size() - 1)) {
      nextInstruction = 0;
    } else {
      nextInstruction = i;
    }
  }

  /**
   *  Sets the source code of this node.
   *
   *  @param sourceCode the source code
   */
  public void setSourceCode(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  /**
   *  Builds the code element set of the node and returns the
   *  list of exceptions occurred during the process.
   *  Be aware that as a side effect, if the code element set
   *  have been built succesfully then any preprocessor rule found
   *  gets registered globally in the {@code InstructionRegistry}.
   *  If the returned list is empty, then {@link Node#buildInstructions()}
   *  should follow this method.
   *
   *  @return the list of exceptions
   *
   *  @throws SourceCodeFormatException If the source code is {@code null}
   *                                    or longer than the permitted maximal size.
   */
  public List<LineNumberedException> buildCodeElementSet()
    throws SourceCodeFormatException
  {
    if (sourceCode == null) {
      throw new SourceCodeFormatException("Source code cannot be null.");
    }

    if (countLines(sourceCode) > maximumSourceCodeLines) {
      throw new SourceCodeFormatException("Source code is longer than maximum.");
    }

    codeElementSet = CodeFactory.createCodeElementSet(
                                    registerMap.keySet(),
                                    portNameSet,
                                    sourceCode);

    if (!(codeElementSet.isExceptionOccurred())) {
      instructionRegistry.putRules(codeElementSet.getRuleMap());
    }

    return codeElementSet.getExceptionList();
  }

  /**
   *  Using the code element set built from the source code, builds the actual
   *  executable instructions. A call to this method must be preceded by
   *  a successful invocation of the {@link Node#buildCodeElementSet} method.
   *  Otherwise it throws a {@code IllegalStateException} exception.
   *
   *  @return the list of exceptions
   *
   *  @throws IllegalStateException If instructions cannot be built from the
   *                                {@code Node}'s code element set.
   */
  public List<LineNumberedException> buildInstructions()
    throws IllegalStateException
  {
    if ((codeElementSet == null) || (codeElementSet.isExceptionOccurred())) {
      throw new IllegalStateException(
        "Instructions cannot be built because of previous errors or empty element set.");
    }

    InstructionFactory instructionFactory =
      new InstructionFactory(instructionRegistry,
                             registerMap,
                             readablePortMap,
                             writeablePortMap);

    List<LineNumberedException> exceptionList =
      instructionFactory.makeInstructions(codeElementSet);

    if (exceptionList.isEmpty()) {
        readyToRun = true;

        instructions = instructionFactory.getInstructions();
    }

    executionState = ExecutionState.IDLE;

    return exceptionList;
  }

  /**
   *  Informs the {@code Node} object that data was read from
   *  the specified port.
   *
   *  @param port the port
   */
  public void onPortRead(CommunicationPort port) {
    blockedWriteablePorts.remove(port);
  }

  /**
   *  Gets the maximum number of source code lines this node can store.
   *
   *  @return the maximum number of source code lines
   */
  public int getMaximumSourceCodeLines() {
    return maximumSourceCodeLines;
  }

  /**
   *  Saves the state of the {@code Node} into a {@code NodeMemento} object. Please
   *  refer to the {@code NodeMemento} class about the saved fields of the {@code Node}.
   *
   *  @return a memento object with the {@code Node}'s state
   */
  public NodeMemento saveToMemento() {
    HashMap<String, int[]> registerValues = new HashMap<>();

    for (Register r : registerMap.values()) {
      registerValues.put(r.getName(), Arrays.copyOf(r.getContents(), r.getCapacity()));
    }

    HashSet<String> ports = new HashSet<>(portNameSet);

    int line;

    if (instructionPointer < instructions.size()) {
      Instruction currentInstruction = instructions.get(instructionPointer);

      line = currentInstruction.getLocation().getLine();
    } else {
      line = 0;
    }

    return new NodeMemento(registerValues, ports,
                           sourceCode,
                           instructionPointer,
                           executionState, line, globalName);
  }

  /**
   *  Resets the {@code Node}'s state to the default. Note that
   *  source code is also deleted along with the registers' contents.
   *  The instruction pointer is set to zero.
   *
   *  The port and register set remains untouched.
   */
  public void reset() {
    for (Register r : registerMap.values()) {
      r.reset();
    }

    instructionPointer = 0;

    nextInstruction = 0;

    blockedWriteablePorts.clear();

    executionState = ExecutionState.IDLE;

    sourceCode = "";

    instructions = null;

    codeElementSet = null;

    readyToRun = false;
  }

  /**
   *  Gets the labels from the current code element set.
   *
   *  @return the labels
   */
  /* package */ Map<String, Integer> getLabels() {
    return codeElementSet == null ? null : codeElementSet.getLabelMap();
  }

  private boolean readDependenciesFulfilled(Instruction instruction) {
    for(CommunicationPort port : instruction.getReadDependencies()) {
      if (!port.hasData()) {
        return false;
      }
    }

    return true;
  }

  private int countLines(String s) {
    Matcher matcher = newlinePattern.matcher(s);

    int lines = 1;

    while (matcher.find()) {
      ++lines;
    }

    return lines;
  }

  /**
   *  Encapsulates the logic of invoking an {@code Instruction}.
   */
  private class Invoker {
    private final Node node;

    private final Instruction instruction;

    private final HashMap<CommunicationPort, MutableInt> writeResults;

    private boolean isPortRead;

    public Invoker(Node node, Instruction instruction) {
      this.node = node;

      this.instruction = instruction;

      writeResults = new HashMap<>();

      isPortRead = false;
    }

    public void invoke() {
      node.setNextInstruction(node.instructionPointer + 1);

      Method m = instruction.getMethod();

      try {
        m.invoke(null, subsituteArguments());

        node.executionState = isPortRead ? ExecutionState.READ : ExecutionState.RUN;

        for (Map.Entry<CommunicationPort, MutableInt> entry : writeResults.entrySet()) {
          CommunicationPort port = entry.getKey();

          port.write(entry.getValue().intValue());

          node.executionState = ExecutionState.WRITE;
        }
      } catch (Exception e) {

      }
    }

    private Object[] subsituteArguments() {
      List<Argument> args = instruction.getActualArguments();

      Object[] ret = new Object[args.size() + 1];

      ret[0] = node.execEnv;

      isPortRead = false;

      for (int i = 0; i < args.size(); ++i) {
        Argument arg = args.get(i);

        switch (arg.getParameterType()) {
          case NUMBER:
            try {
              ret[i + 1] = Integer.parseInt(arg.getValue());
            } catch (NumberFormatException nfe) {
              ret[i + 1] = 0;
            }

            break;
          case LABEL:
            ret[i + 1] = arg.getValue();

            break;
          case READ_PORT:
            CommunicationPort readPort = node.readablePortMap.get(arg.getValue());

            int[] portContents = readPort.readContents();

            ret[i + 1] = new ReadResult(portContents[0]);

            isPortRead = true;

            break;
          case WRITE_PORT:
            CommunicationPort writePort = node.writeablePortMap.get(arg.getValue());

            MutableInt writeResult = new MutableInt();

            node.blockedWriteablePorts.add(writePort);

            writeResults.put(writePort, writeResult);

            ret[i + 1] = writeResult;

            break;
          case REGISTER:
            Register register = node.registerMap.get(arg.getValue());

            int[] registerContents = register.getContents();

            ret[i + 1] = registerContents;

            break;
          default:
            ret[i + 1] = null;

            break;
        }
      }

      return ret;
    }
  }
}
