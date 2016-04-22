package hu.progtech.cd2t100.emulator;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;

import hu.progtech.cd2t100.asm.LineNumberedException;

import hu.progtech.cd2t100.computation.Node;
import hu.progtech.cd2t100.computation.NodeExecutionException;
import hu.progtech.cd2t100.computation.SourceCodeFormatException;

import hu.progtech.cd2t100.computation.io.CommunicationPort;

/**
 *  Emulates a multi-node processor architecture by controlling
 *  {@code Node}s and ports. The state changes of the {@code Emulator}
 *  can be observed with an {@code EmulatorObserver}. The actual execution
 *  data of the last processor cycle (the nodes' mementos and the contents of the ports)
 *  is published in a blocking queue.
 *
 *  If the emulator is in {@code RUNNING} state, its driven by a clock generator
 *  ticking at a specified frequency passed to the constructor. That way the
 *  emulator can operate as fast as one may wish.
 */
public class Emulator {
  private Timer clockGenerator;

  private final BlockingQueue<EmulatorCycleData> cycleDataQueue;

  private final long clockFrequency;

  private final Map<String, Node> nodes;

  private final List<CommunicationPort> communicationPorts;

  private Map<String, List<LineNumberedException>> codeExceptionMap;

  private Map<String, Exception> nodeExceptionMap;

  private EmulatorState emulatorState;

  private final EmulatorObserver emulatorObserver;

  private EmulatorCycle currentCycle;

  /**
   *  Constructs a new {@code Emulator} with the specified nodes and ports running
   *  at the passed frequency. The newly created {@code Emulator} will be in
   *  {@code STOPPED} state.
   *
   *  @param nodes the nodes
   *  @param communicationPorts the ports between the nodes
   *  @param emulatorObserver the observer obversing the state changes
   *  @param clockFrequency the time between the clock generator's ticks in milliseconds
   */
  Emulator(Map<String, Node> nodes, List<CommunicationPort> communicationPorts,
           EmulatorObserver emulatorObserver, long clockFrequency) {
    this.nodes = nodes;

    this.communicationPorts = communicationPorts;

    this.emulatorObserver = emulatorObserver;

    emulatorObserver.setEmulator(this);

    this.clockFrequency = clockFrequency;

    clockGenerator = new Timer(true);

    cycleDataQueue = new SynchronousQueue<>();

    emulatorState = EmulatorState.STOPPED;
  }

  /**
   *  Gets the state of the {@code Emulator}.
   *
   *  @return the state of the {@code Emulator}
   */
  public EmulatorState getState() {
    synchronized (emulatorState) {
      return emulatorState;
    }
  }

  /**
   *  Sends a request to the {@code Emulator} to change it's state.
   *  The states react differently to the request types, please refer to the
   *  documentation of the {@link EmulatorState}.
   *
   *  @param stateChangeRequest the state change request's type
   */
  public void request(StateChangeRequest stateChangeRequest) {
    synchronized (emulatorState) {
      emulatorState.onRequest(this, stateChangeRequest);
    }
  }

  /**
   *  Gets the time between the clock generator tick in milliseconds.
   *
   *  @return the time between clock generator ticks
   */
  public long getClockFrequency() {
    return clockFrequency;
  }

  /**
   *  Sets the source code of a specified {@code Node}.
   *
   *  @param nodeName the global name if the {@code Node}
   *  @param sourceCode the source code
   *
   *  @throws IllegalStateException If the {@code Emulator} is not in
   *                                {@code STOPPED} state.
   */
  public void setSourceCode(String nodeName, String sourceCode)
    throws IllegalStateException
  {
    if (getState() != EmulatorState.STOPPED) {
      throw new IllegalStateException("Source code can only be set in STOPPED state.");
    }

    Optional.ofNullable(nodes.get(nodeName))
            .ifPresent(x -> x.setSourceCode(sourceCode));
  }

  /**
   *  Gets the queue that contains data from the last processor cycle.
   *
   *  @return the queue with the data
   */
  public BlockingQueue<EmulatorCycleData> getCycleDataQueue() {
    return cycleDataQueue;
  }

  /**
   *  Gets the code exception map. Code exceptions are caused by syntactically
   *  or semantically incorrect source code. The returned map contains the exceptions
   *  mapped to the global names of the nodes.
   *
   *  @return the map of code exceptions
   */
  public Map<String, List<LineNumberedException>> getCodeExceptionMap() {
    return codeExceptionMap;
  }

  /**
   *  Gets the node exception map. Node exceptions are caused by too lengthy
   *  or {@code null} source codes. The returned map contains the exceptions
   *  mapped to the global names of the nodes.
   *
   *  @return the map of node exceptions
   */
  public Map<String, Exception> getNodeExceptionMap() {
    return nodeExceptionMap;
  }

  /* package */ void start(ExecutionMode executionMode) {
    currentCycle = new EmulatorCycle(executionMode);

    clockGenerator.scheduleAtFixedRate(
      currentCycle, 0, clockFrequency);
  }


  /* package */ void pause() {
    currentCycle.cancel();

    currentCycle = null;
  }

  /* package */ void stop() {
    if (currentCycle != null) {
      currentCycle.cancel();

      currentCycle = null;
    }

    reset();
  }

  private void reset() {
    for (CommunicationPort port : communicationPorts) {
      port.reset();
    }

    for (Node node : nodes.values()) {
      node.reset();
    }
  }

  /* package */ void setState(EmulatorState emulatorState) {
    synchronized (emulatorState) {
      this.emulatorState = emulatorState;
    }

    emulatorObserver.onStateChanged(emulatorState);
  }

  /* package */ StateChangeRequest generateInstructions() {
    nodeExceptionMap = new HashMap<>();

    codeExceptionMap = new HashMap<>();

    for (Node node : nodes.values()) {
      List<LineNumberedException> exceptions;

      try {
        exceptions = node.buildCodeElementSet();
      } catch(SourceCodeFormatException e) {
        nodeExceptionMap.put(node.getGlobalName(), e);

        continue;
      }

      if (!exceptions.isEmpty()) {
        codeExceptionMap.put(node.getGlobalName(), exceptions);

        continue;
      }

      try {
        exceptions = node.buildInstructions();
      } catch (NodeExecutionException e) {
        nodeExceptionMap.put(node.getGlobalName(), e);

        continue;
      }

      if (!exceptions.isEmpty()) {
        codeExceptionMap.put(node.getGlobalName(), exceptions);
      }
    }

    if (!codeExceptionMap.isEmpty() || !nodeExceptionMap.isEmpty()) {
      return StateChangeRequest.ERROR;
    }

    return StateChangeRequest.RUN;
  }

  private class EmulatorCycle extends TimerTask {
    private final ExecutionMode executionMode;

    public EmulatorCycle(ExecutionMode executionMode) {
      this.executionMode = executionMode;
    }

    @Override
    public void run() {
      EmulatorCycleData data = new EmulatorCycleData();

      for (CommunicationPort cp : communicationPorts) {
        data.addPortValue(cp);

        cp.step();
      }

      for (Node n : nodes.values()) {
        try {
          data.addNodeMemento(n.saveToMemento());

          n.step();
        } catch (NodeExecutionException e) {

        }
      }

      try {
        cycleDataQueue.put(data);
      } catch (InterruptedException e) {

      }

      if (executionMode == ExecutionMode.STEPPED) {
        cancel();

        request(StateChangeRequest.PAUSE);
      }
    }
  }
}
