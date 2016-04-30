package hu.progtech.cd2t100.computation;

import java.io.InputStream;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.rules.ExpectedException;

import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class NodeTest {
  private static String[] INSTRUCTION_FILES =
    { "Jmp", "Add", "Set", "Mov", "Jro" };

  private static InstructionRegistry registry;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private Node node;

  private Register acc;

  private CommunicationPort readable;
  private CommunicationPort writeable;

  @BeforeClass
  public static void setUpInstructions() {
    registry = new InstructionRegistry(new HashMap<>());

    for (String fileFragment : INSTRUCTION_FILES) {
      String fileName = "validGroovy/" + fileFragment + ".groovy";

      InputStream is =
        NodeTest.class.getClassLoader().getResourceAsStream(fileName);

      try {
        InstructionInfo info = InstructionLoader.loadInstruction(is);

        registry.registerInstruction(info);
      } catch (Exception e) {
        Assert.fail("Could not load instructions.");
      }
    }
  }

  @Before
  public void setUp() {
    registry.resetRules();

    acc = new Register(1, "ACC");

    readable = new CommunicationPort("CP1");

    writeable = new CommunicationPort("CP2");

    node = new NodeBuilder()
            .setGlobalName("NODE")
            .setInstructionRegistry(registry)
            .setMaximumSourceCodeLines(5)
            .addRegister(acc)
            .addReadablePort("UP", readable)
            .addWriteablePort("DOWN", writeable)
            .build();
  }

  @Test
  public void codeElementSetTestNull() throws Exception {
    thrown.expect(SourceCodeFormatException.class);

    node.setSourceCode(null);

    node.buildCodeElementSet();
  }

  @Test
  public void codeElementSetTestTooLong() throws Exception {
    thrown.expect(SourceCodeFormatException.class);

    node.setSourceCode("\n\n\n\n\n\n");

    node.buildCodeElementSet();
  }

  @Test
  public void codeElementSetTestException() throws SourceCodeFormatException {
    node.setSourceCode("ADD UNKNOWN\n");

    List<LineNumberedException> list = node.buildCodeElementSet();

    Assert.assertEquals("Returned list must not be empty.",
                        1, list.size());
  }

  @Test
  public void codeElementSetTestPutRule() throws SourceCodeFormatException {
    node.setSourceCode("!overflow OFF\n");

    List<LineNumberedException> list = node.buildCodeElementSet();

    Assert.assertEquals("Returned list must be empty.",
                        0, list.size());

    Assert.assertEquals("Rule value must have been put into map.",
                        "OFF",
                        registry.getRuleValue("overflow"));
  }

  @Test
  public void buildInstructionsTestNull() throws Exception {
    thrown.expect(IllegalStateException.class);

    node.buildInstructions();
  }

  @Test
  public void buildInstructionsTestPreviousException() throws Exception {
    thrown.expect(IllegalStateException.class);

    node.setSourceCode("ADD UNKNOWN\n");

    node.buildCodeElementSet();

    node.buildInstructions();
  }

  @Test
  public void buildInstructionsTestException() throws Exception {
    node.setSourceCode("UNKNOWN\n");

    node.buildCodeElementSet();

    List<LineNumberedException> list = node.buildInstructions();

    Assert.assertEquals("Returned list must not be empty.",
                        1, list.size());
  }

  @Test
  public void buildInstructionsTestEmpty() throws Exception {
    node.buildCodeElementSet();

    List<LineNumberedException> list = node.buildInstructions();

    Assert.assertEquals("Returned list must be empty.",
                        0, list.size());
  }

  @Test
  public void getGlobalNameTest() {
    Assert.assertEquals("Global name must be NODE.",
                        "NODE",
                        node.getGlobalName());
  }

  @Test
  public void getMaximumSourceCodeLinesTest() {
    Assert.assertEquals("Maximum number of source code lines must be 5",
                        5,
                        node.getMaximumSourceCodeLines());
  }

  @Test
  public void getLabelsTestNull() {
    Assert.assertEquals("Returned map must be null.",
                        null, node.getLabels());
  }

  @Test
  public void getLabelsTestValid() throws Exception {
    node.setSourceCode("label:\n");

    Map<String, Integer> labels = new HashMap<>();

    labels.put("label", 0);

    node.buildCodeElementSet();

    Assert.assertEquals("Labels must match.",
                        labels, node.getLabels());
  }

  @Test
  public void stepTestNotReady1() throws Exception {
    thrown.expect(IllegalStateException.class);

    node.step();
  }

  @Test
  public void stepTestNotReady2() throws Exception {
    thrown.expect(IllegalStateException.class);

    node.setSourceCode("UNKNOWN\n");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();
  }

  @Test
  public void stepTestEmpty() throws Exception {
    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();
  }

  @Test
  public void stepTestAdd() throws Exception {
    node.setSourceCode("ADD 1 ACC\n");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    NodeMemento memento = node.saveToMemento();

    Map<String, int[]> registerValues = memento.getRegisterValues();

    int[] expected = { 1 };

    Assert.assertArrayEquals("The contents of ACC must be [1].",
                             expected, registerValues.get("ACC"));
  }

  @Test
  public void stepTestJroLess() throws Exception {
    node.setSourceCode("JRO -10\nADD 1 ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    int pointer = node.getInstructionPointer();

    Assert.assertEquals("Pointer must be set to zero.",
                        0, pointer);

    node.step();

    NodeMemento memento = node.saveToMemento();

    Map<String, int[]> registerValues = memento.getRegisterValues();

    int[] expected = { 0 };

    Assert.assertArrayEquals("The contents of ACC must be [0].",
                             expected, registerValues.get("ACC"));
  }

  @Test
  public void stepTestJroMore() throws Exception {
    node.setSourceCode("JRO 10\nADD 1 ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    int pointer = node.getInstructionPointer();

    Assert.assertEquals("Pointer must be set to zero.",
                        0, pointer);

    node.step();

    NodeMemento memento = node.saveToMemento();

    Map<String, int[]> registerValues = memento.getRegisterValues();

    int[] expected = { 0 };

    Assert.assertArrayEquals("The contents of ACC must be [0].",
                             expected, registerValues.get("ACC"));
  }

  @Test
  public void stepTestJmp() throws Exception {
    node.setSourceCode("JMP LABEL\nADD 1 ACC\nLABEL: ADD 2 ACC\n");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    int pointer = node.getInstructionPointer();

    Assert.assertEquals("Pointer must be set to the appropiate value.",
                        2, pointer);
  }

  @Test
  public void stepTestReadDependency() throws Exception {
    node.setSourceCode("MOV UP ACC\nADD 1 ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    int pointer = node.getInstructionPointer();

    Assert.assertEquals("Pointer must be set to 0.",
                        0, pointer);

    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Execution state must be IDLE due to the read dependency.",
                        ExecutionState.IDLE,
                        memento.getExecutionState());
  }

  @Test
  public void stepTestReadDependencyFulfilled() throws Exception {
    node.setSourceCode("MOV UP ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    readable.write(1);

    readable.step();

    node.step();

    int pointer = node.getInstructionPointer();

    Assert.assertEquals("Pointer must be set to 0.",
                        0, pointer);

    NodeMemento memento = node.saveToMemento();

    int[] expected = { 1 };

    Assert.assertArrayEquals("ACC must be the read value.",
                             expected,
                             memento.getRegisterValues().get("ACC"));
  }

  @Test
  public void stepTestWriteDependency() throws Exception {
    node.setSourceCode("MOV ACC DOWN\nADD 1 ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Execution state must be WRITE due to the write dependency.",
                        ExecutionState.WRITE  ,
                        memento.getExecutionState());

    int[] expected = { 0 };

    Assert.assertArrayEquals("Value must have been written on the port.",
                             expected, writeable.getContents());

    node.step();

    memento = node.saveToMemento();

    Assert.assertArrayEquals("ACC must be the zero.",
                             expected, memento.getRegisterValues().get("ACC"));

    Assert.assertEquals("Instruction pointer should not changed.",
                        1, node.getInstructionPointer());
  }

  @Test
  public void onPortReadTest() throws Exception {
    node.setSourceCode("MOV ACC DOWN\nADD 1 ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Execution state must be WRITE due to the write dependency.",
                        ExecutionState.WRITE  ,
                        memento.getExecutionState());

    int[] expected = { 0 };

    Assert.assertArrayEquals("Value must have been written on the port.",
                             expected, writeable.getContents());

    node.onPortRead(writeable);

    node.step();

    Assert.assertEquals("Instruction pointer must be 0.",
                        0, node.getInstructionPointer());

    int[] accExpected = { 1 };

    memento = node.saveToMemento();

    Assert.assertArrayEquals("ACC must have been be increased.",
                             accExpected,
                             memento.getRegisterValues().get("ACC"));
  }

  @Test
  public void resetTest() throws Exception {
    node.setSourceCode("ADD 1 ACC\nADD 1 ACC");

    node.buildCodeElementSet();

    node.buildInstructions();

    node.step();

    node.reset();

    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Execution state must be IDLE due to reset().",
                        ExecutionState.IDLE,
                        memento.getExecutionState());

    int[] expected = { 0 };

    Assert.assertArrayEquals("ACC value must be zero.",
                             expected,
                             memento.getRegisterValues().get("ACC"));

    Assert.assertEquals("Instruction pointer must be 0.",
                        0, node.getInstructionPointer());
  }
}
