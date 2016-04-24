package hu.progtech.cd2t100.game.util;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.progtech.cd2t100.computation.InstructionRegistry;
import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.NodeBuilder;

import hu.progtech.cd2t100.computation.io.*;

import hu.progtech.cd2t100.emulator.Emulator;
import hu.progtech.cd2t100.emulator.EmulatorBuilder;
import hu.progtech.cd2t100.emulator.EmulatorObserver;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.game.model.CommunicationPortDescriptor;
import hu.progtech.cd2t100.game.model.InputPortDescriptor;
import hu.progtech.cd2t100.game.model.OutputPortDescriptor;
import hu.progtech.cd2t100.game.model.RegisterDescriptor;
import hu.progtech.cd2t100.game.model.PortNameMapping;

public class EmulatorFactory {
  private static final Logger logger =
    LoggerFactory.getLogger(EmulatorFactory.class);

  private static long defaultClockFrequency = 1000;

  private final InstructionRegistry registry;

  private EmulatorFactory(InstructionRegistry registry) {
    this.registry = registry;
  }

  public static EmulatorFactory newInstance(InstructionRegistry registry) {
    return new EmulatorFactory(registry);
  }

  public static long getDefaultClockFrequency() {
    return defaultClockFrequency;
  }

  public static void setDefaultClockFrequency(long frequency) {
    defaultClockFrequency = frequency;
  }

  public Emulator emulatorFromPuzzle(Puzzle puzzle, EmulatorObserver observer) {
    EmulatorBuilder emulatorBuilder = new EmulatorBuilder();

    logger.info("Building emulator from puzzle.");

    emulatorBuilder.setObserver(observer)
                   .setClockFrequency(defaultClockFrequency);

    Map<String, CommunicationPort> ports =
      createPorts(puzzle);

    logger.debug("Ports created: {}", ports);

    for (CommunicationPort port : ports.values()) {
      emulatorBuilder.addCommunicationPort(port);
    }

    List<Node> nodes = createNodes(puzzle, ports);

    logger.debug("Nodes created: {}", nodes);

    for (Node node : nodes) {
      emulatorBuilder.addNode(node);
    }

    Emulator emulator = emulatorBuilder.build();

    logger.info("Emulator succesfully built.");

    return emulator;
  }

  private Map<String, CommunicationPort> createPorts(Puzzle puzzle) {
    Map<String, CommunicationPort> ports = new HashMap<>();

    for (CommunicationPortDescriptor port :
          puzzle.getCommunicationPortDescriptors())
    {
      String globalName = port.getGlobalName();

      ports.put(globalName, new CommunicationPort(globalName));
    }

    for (OutputPortDescriptor port :
          puzzle.getOutputPortDescriptors())
    {
      String globalName = port.getGlobalName();

      ports.put(globalName, new OutputPort(globalName));
    }

    for (InputPortDescriptor port :
          puzzle.getInputPortDescriptors())
    {
      String globalName = port.getGlobalName();

      /*
       *  Function.indentity() is not useful here sadly because of the
       *  difference between the types (boxed and unboxed type).
       */
      ports.put(globalName,
                new InputPort(globalName, port.getContents()
                                              .stream()
                                              .mapToInt(x -> x)
                                              .toArray()));
    }

    return ports;
  }

  private List<Node> createNodes(Puzzle puzzle,
                                 Map<String, CommunicationPort> ports)
  {
    List<Node> nodes = new ArrayList<>();

    for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
      NodeBuilder nodeBuilder = new NodeBuilder();

      nodeBuilder.setGlobalName(descriptor.getGlobalName())
                 .setMaximumSourceCodeLines(descriptor.getMaximumSourceCodeLines())
                 .setInstructionRegistry(registry);

      for (RegisterDescriptor register : descriptor.getRegisterDescriptors()) {
        nodeBuilder.addRegister(new Register(register.getCapacity(),
                                             register.getName()));
      }

      for (PortNameMapping mapping : descriptor.getReadablePorts()) {
        nodeBuilder.addReadablePort(mapping.getLocalName(),
                                    ports.get(mapping.getGlobalName()));
      }

      for (PortNameMapping mapping : descriptor.getWriteablePorts()) {
        nodeBuilder.addWriteablePort(mapping.getLocalName(),
                                     ports.get(mapping.getGlobalName()));
      }

      nodes.add(nodeBuilder.build());
    }

    return nodes;
  }
}
