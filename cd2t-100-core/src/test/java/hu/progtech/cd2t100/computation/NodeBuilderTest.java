package hu.progtech.cd2t100.computation;

import java.util.HashMap;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import hu.progtech.cd2t100.computation.io.*;

public class NodeBuilderTest {
  private InstructionRegistry registry;

  @Before
  public void setUp() {
    registry = new InstructionRegistry(new HashMap<>());
  }

  @Test
  public void buildEmptyNode() {
    NodeBuilder builder = new NodeBuilder();

    Node built = builder.setGlobalName("NODE")
                        .setInstructionRegistry(registry)
                        .setMaximumSourceCodeLines(20)
                        .build();

    Node instantiated =
      new Node(registry, 20, "NODE",
               new HashMap<>(), new HashMap<>(), new HashMap<>());

    Assert.assertEquals("Built and instantiated Node must be equal.",
                        built, instantiated);
  }

  @Test
  public void buildNodeWithOnlyRegisters() {
    NodeBuilder builder = new NodeBuilder();

    Register acc = new Register(1, "ACC"),
             bak = new Register(1, "BAK");

    HashMap<String, Register> registerMap = new HashMap<>();

    registerMap.put("ACC", acc);
    registerMap.put("BAK", bak);

    Node built = builder.setGlobalName("NODE")
                        .setInstructionRegistry(registry)
                        .setMaximumSourceCodeLines(20)
                        .addRegister(acc)
                        .addRegister(bak)
                        .build();

    Node instantiated =
      new Node(registry, 20, "NODE",
               registerMap, new HashMap<>(), new HashMap<>());

    Assert.assertEquals("Built and instantiated Node must be equal.",
                        built, instantiated);
  }

  @Test
  public void buildNodeWithOnlyReadablePorts() {
    NodeBuilder builder = new NodeBuilder();

    CommunicationPort cp1 = new CommunicationPort("CP1"),
                      cp2 = new CommunicationPort("CP2");

    HashMap<String, CommunicationPort> portMap = new HashMap<>();

    portMap.put("UP", cp1);
    portMap.put("DOWN", cp2);

    Node built = builder.setGlobalName("NODE")
                        .setInstructionRegistry(registry)
                        .setMaximumSourceCodeLines(20)
                        .addReadablePort("UP", cp1)
                        .addReadablePort("DOWN", cp2)
                        .build();

    Node instantiated =
      new Node(registry, 20, "NODE",
               new HashMap<>(), portMap, new HashMap<>());

    Assert.assertEquals("Built and instantiated Node must be equal.",
                        built, instantiated);
  }

  @Test
  public void buildNodeWithOnlyWriteablePorts() {
    NodeBuilder builder = new NodeBuilder();

    CommunicationPort cp1 = new CommunicationPort("CP1"),
                      cp2 = new CommunicationPort("CP2");

    HashMap<String, CommunicationPort> portMap = new HashMap<>();

    portMap.put("UP", cp1);
    portMap.put("DOWN", cp2);

    Node built = builder.setGlobalName("NODE")
                        .setInstructionRegistry(registry)
                        .setMaximumSourceCodeLines(20)
                        .addWriteablePort("UP", cp1)
                        .addWriteablePort("DOWN", cp2)
                        .build();

    Node instantiated =
      new Node(registry, 20, "NODE",
               new HashMap<>(), new HashMap<>(), portMap);

    Assert.assertEquals("Built and instantiated Node must be equal.",
                        built, instantiated);
  }

  @Test
  public void buildNodeWithEverything() {
    NodeBuilder builder = new NodeBuilder();

    CommunicationPort cp1 = new CommunicationPort("CP1"),
                      cp2 = new CommunicationPort("CP2");

    Register acc = new Register(1, "ACC");

    HashMap<String, Register> registerMap = new HashMap<>();
    HashMap<String, CommunicationPort> readMap = new HashMap<>();
    HashMap<String, CommunicationPort> writeMap = new HashMap<>();

    registerMap.put("ACC", acc);
    readMap.put("UP", cp1);
    writeMap.put("DOWN", cp2);

    Node built = builder.setGlobalName("NODE")
                        .setInstructionRegistry(registry)
                        .setMaximumSourceCodeLines(20)
                        .addRegister(acc)
                        .addReadablePort("UP", cp1)
                        .addWriteablePort("DOWN", cp2)
                        .build();

    Node instantiated =
      new Node(registry, 20, "NODE",
               registerMap, readMap, writeMap);

    Assert.assertEquals("Built and instantiated Node must be equal.",
                        built, instantiated);
  }
}
