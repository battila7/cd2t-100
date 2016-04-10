package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;
import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class Node {
  private static Pattern newlinePattern =
    Pattern.compile("\r\n|\r|\n");

  private final Map<String, Register> registerMap;
  private final Map<String, CommunicationPort> readablePortMap;
  private final Map<String, CommunicationPort> writeablePortMap;

  private final HashSet<String> portNameSet;

  private final int maximumSourceCodeLines;

  private final InstructionRegistry instructionRegistry;

  private String sourceCode;

  private boolean readyToRun;

  private ArrayList<Instruction> instructions;

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

    portNameSet.addAll(writeablePortMap.keySet());

    readyToRun = false;
  }

  public void step() throws NodeExecutionException {
    if (!readyToRun) {
      throw new NodeExecutionException(
        "Node's not able to run because of previous errors.");
    }
  }

  public void setSourceCode(String sourceCode)
    throws SourceCodeFormatException
  {
    if (countLines(sourceCode) > maximumSourceCodeLines) {
      throw new SourceCodeFormatException("Source code is longer than maximum.");
    }

    this.sourceCode = sourceCode;
  }

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

  public List<LineNumberedException> buildInstructions() {
    if ((codeElementSet == null) || (codeElementSet.isExceptionOccurred())) {
      // gonna throw some exception
    }

    InstructionFactory instructionFactory =
      new InstructionFactory(instructionRegistry,
                             registerMap,
                             readablePortMap,
                             writeablePortMap);

    List<LineNumberedException> exceptionList =
      instructionFactory.makeInstructions(codeElementSet);

    readyToRun = exceptionList.isEmpty();

    return exceptionList;
  }

  public int getMaximumSourceCodeLines() {
    return maximumSourceCodeLines;
  }

  private int countLines(String s) {
    Matcher matcher = newlinePattern.matcher(s);

    int lines = 1;

    while (matcher.find()) {
      ++lines;
    }

    return lines;
  }
}
