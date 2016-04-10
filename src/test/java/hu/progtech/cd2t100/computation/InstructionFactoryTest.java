package hu.progtech.cd2t100.computation;

import java.io.InputStream;

import java.util.List;
import java.util.HashSet;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;
import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.formal.InstructionInfo;
import hu.progtech.cd2t100.formal.InstructionLoader;

import hu.progtech.cd2t100.computation.io.Register;
import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class InstructionFactoryTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private InstructionRegistry instructionRegistry;

  private InstructionFactory instructionFactory;

  private HashMap<String, Register> registerMap;
  private HashMap<String, CommunicationPort> readablePortMap;
  private HashMap<String, CommunicationPort> writeablePortMap;

  private HashSet<String> allPortNames;

  private static InputStream getCodeStream(String resourceName) {
    return InstructionRegistryTest.class
                                  .getClassLoader()
                                  .getResourceAsStream(resourceName);
  }

  @Before
  public void setUp() {
    instructionRegistry = new InstructionRegistry();

    registerMap = new HashMap<>();
    readablePortMap = new HashMap<>();
    writeablePortMap = new HashMap<>();

    registerMap.put("ACC", new Register(1, "ACC"));
    registerMap.put("BAK", new Register(1, "BAK"));

    readablePortMap.put("UP", new CommunicationPort());
    readablePortMap.put("DOWN", new CommunicationPort());

    writeablePortMap.put("UP", new CommunicationPort());
    writeablePortMap.put("LEFT", new CommunicationPort());

    allPortNames = new HashSet<>(readablePortMap.keySet());
    allPortNames.addAll(writeablePortMap.keySet());

    instructionFactory =
      new InstructionFactory(instructionRegistry, registerMap,
                             readablePortMap, writeablePortMap);
  }

  @Test
  public void unknownOpcode() throws Exception {
    HashSet<String> allPortNames = new HashSet<>(readablePortMap.keySet());
    allPortNames.addAll(writeablePortMap.keySet());

    CodeElementSet elementSet =
      CodeFactory.createCodeElementSet(registerMap.keySet(), allPortNames,
                                       "EMPTY");

    List<LineNumberedException> exceptionList =
      instructionFactory.makeInstructions(elementSet);

    Assert.assertEquals("One exception must have been thrown.",
                        1, exceptionList.size());

    Assert.assertTrue("Exception must be instance of UnknownOpcodeException.",
                      exceptionList.get(0) instanceof UnknownOpcodeException);
  }

  @Test
  public void singleInstructionWithoutArguments() throws Exception {
    InstructionInfo info =
      InstructionLoader.loadInstruction(getCodeStream("EmptyInstruction.groovy"));

    instructionRegistry.registerInstruction(info);

    CodeElementSet elementSet =
      CodeFactory.createCodeElementSet(registerMap.keySet(), allPortNames,
                                       "EMPTY");

    List<LineNumberedException> exceptionList =
      instructionFactory.makeInstructions(elementSet);

    Assert.assertTrue("No exceptions should have been thrown.",
                      exceptionList.isEmpty());

    List<Instruction> instructions = instructionFactory.getInstructions();

    Assert.assertEquals("Only one instruction should be present.",
                        1, instructions.size());

    Instruction i = instructions.get(0);

    Assert.assertSame("Instruction method and only info method must be the same.",
                       i.getMethod(),
                       info.getPossibleCalls().get(0).getBackingMethod());
  }

  @Test
  public void unsetRuleTest() throws Exception {
    InstructionInfo info =
      InstructionLoader.loadInstruction(getCodeStream("TestInstruction.groovy"));

    instructionRegistry.registerInstruction(info);

    CodeElementSet elementSet =
      CodeFactory.createCodeElementSet(registerMap.keySet(), allPortNames,
                                       "TEST");

    List<LineNumberedException> exceptionList =
      instructionFactory.makeInstructions(elementSet);

    Assert.assertEquals("One exception must have been thrown.",
                        1, exceptionList.size());

    Assert.assertTrue("Exception must be instance of PreprocessorRuleUnsetException.",
                      exceptionList.get(0) instanceof PreprocessorRuleUnsetException);
  }

  @Test
  public void ambiguousTest() throws Exception {
    HashMap<String, String> rules = new HashMap<>();

    rules.put("clampat", "999");

    instructionRegistry.putRules(rules);

    InstructionInfo info =
      InstructionLoader.loadInstruction(getCodeStream("TestInstruction.groovy"));

    instructionRegistry.registerInstruction(info);

    CodeElementSet elementSet =
      CodeFactory.createCodeElementSet(registerMap.keySet(), allPortNames,
                                       "TEST");

    List<LineNumberedException> exceptionList =
      instructionFactory.makeInstructions(elementSet);

    Assert.assertEquals("One exception must have been thrown.",
                        1, exceptionList.size());

    Assert.assertTrue("Exception must be instance of ArgumentMatchingException.",
                      exceptionList.get(0) instanceof ArgumentMatchingException);
  }
}
