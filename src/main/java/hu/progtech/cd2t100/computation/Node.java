package hu.progtech.cd2t100.computation;

import java.lang.reflect.Method;

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

public class Node {
  private static Pattern newlinePattern =
    Pattern.compile("\r\n|\r|\n");

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

  public Node(InstructionRegistry instructionRegistry,
              int maximumSourceCodeLines,
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

    portNameSet.addAll(writeablePortMap.keySet());

    execEnv = new ExecutionEnvironment(this);

    readyToRun = false;
  }

  public void step() throws NodeExecutionException {
    if (!readyToRun) {
      throw new NodeExecutionException(
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
      return;
    }

    Invoker invoker = new Invoker(this, currentInstruction);

    invoker.invoke();

    instructionPointer = nextInstruction;
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
  public void setSourceCode(String sourceCode)
    throws SourceCodeFormatException
  {
    if (countLines(sourceCode) > maximumSourceCodeLines) {
      throw new SourceCodeFormatException("Source code is longer than maximum.");
    }

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
   */
  public List<LineNumberedException> buildCodeElementSet()
    throws SourceCodeFormatException
  {
    if (sourceCode == null) {
      throw new SourceCodeFormatException("Source code cannot be null.");
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

  public List<LineNumberedException> buildInstructions()
    throws NodeExecutionException
  {
    if ((codeElementSet == null) || (codeElementSet.isExceptionOccurred())) {
      throw new NodeExecutionException(
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

    return exceptionList;
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

  class Invoker {
    private final Node node;

    private final Instruction instruction;

    private final HashMap<CommunicationPort, MutableInt> writeResults;

    public Invoker(Node node, Instruction instruction) {
      this.node = node;

      this.instruction = instruction;

      writeResults = new HashMap<>();
    }

    public void invoke() {
      node.setNextInstruction(node.instructionPointer + 1);

      Method m = instruction.getMethod();

      try {
        m.invoke(null, subsituteArguments());

        for (Map.Entry<CommunicationPort, MutableInt> entry : writeResults.entrySet()) {
          CommunicationPort port = entry.getKey();

          port.write(entry.getValue().intValue());
        }
      } catch (Exception e) {

      }
    }

    private Object[] subsituteArguments() {
      List<Argument> args = instruction.getActualArguments();

      Object[] ret = new Object[args.size() + 1];

      ret[0] = node.execEnv;

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
