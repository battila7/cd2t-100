package hu.progtech.cd2t100.computation;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class NodeMementoTest {
  private Node node;

  @Before
  public void setUp() {
    Register acc = new Register(1, "ACC");

    HashMap<String, Register> registerMap = new HashMap<>();

    registerMap.put("ACC", acc);

    HashMap<String, CommunicationPort> portMap = new HashMap();

    portMap.put("UP", new CommunicationPort("CP1"));

    this.node = new Node(new InstructionRegistry(new HashMap<>()), 20, "NODE",
                    registerMap, portMap, new HashMap<>());
  }

  @Test
  public void getRegisterValuesTest() {
    NodeMemento memento = node.saveToMemento();

    HashMap<String, int[]> valueMap = new HashMap<>();

    valueMap.put("ACC", new int[1]);

    Map<String, int[]> mementoRegisters = memento.getRegisterValues();

    Assert.assertEquals("Register count must be one", 1,
                        mementoRegisters.size());

    Assert.assertArrayEquals("Registers should be empty.",
                             valueMap.get(0),
                             mementoRegisters.get(0));
  }

  @Test
  public void getPortNameSetTest() {
    NodeMemento memento = node.saveToMemento();

    Set<String> ports = new HashSet<>();

    ports.add("UP");

    Assert.assertEquals("Port names must be equal.",
                        ports,
                        memento.getPortNameSet());
  }

  @Test
  public void getSourceCodeTest() {
    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Source code must be the empty string.",
                        "",
                        memento.getSourceCode());
  }

  @Test
  public void getInstructionPointerTest() {
    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Instruction pointer must be at 0.",
                        0,
                        memento.getInstructionPointer());
  }

  @Test
  public void getExecutionStateTest() {
    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("State must be IDLE.",
                        ExecutionState.IDLE,
                        memento.getExecutionState());
  }

  @Test
  public void getCurrentLineTest() {
    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Current line must be 0.",
                        0,
                        memento.getCurrentLine());
  }

  @Test
  public void getGloblNameTest() {
    NodeMemento memento = node.saveToMemento();

    Assert.assertEquals("Global name must be NODE",
                        "NODE",
                        memento.getGlobalName());
  }

  @Test
  public void toStringTest() {
    NodeMemento memento = node.saveToMemento();

    String expected = "NODE(IDLE) @l: 0\n"
                    + "IP: 0\n"
                    + "\tACC = [0]\n";

    Assert.assertEquals("String representations must match.",
                        expected,
                        memento.toString());
  }
}
