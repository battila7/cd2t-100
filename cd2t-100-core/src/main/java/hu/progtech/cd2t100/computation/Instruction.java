package hu.progtech.cd2t100.computation;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Set;

import hu.progtech.cd2t100.asm.Location;

import hu.progtech.cd2t100.computation.io.CommunicationPort;

/**
 *  An executable instruction created from an {@code InstructionElement}, after
 *  being matched agaist an {@code InstructionInfo}. Contains a {@code java.lang.reflect.Method}
 *  object, the actual arguments and the location of the original instruction
 *  element along with the read dependencies. A read dependency is a port the
 *  {@code Instruction} reads from.
 *
 *  @see hu.progtech.cd2t100.asm.InstructionElement
 *  @see hu.progtech.cd2t100.formal.InstructionInfo
 */
public class Instruction {
  private final Method method;

  private final List<Argument> actualArguments;

  private final Set<CommunicationPort> readDependencies;

  private final Location location;

  /**
   *  Constructs a new {@code Instruction} with the specified method, arguments
   *  read dependencies and location.
   *
   *  @param method the executable method of the instruction
   *  @param actualArguments the list of actual arguments
   *  @param readDependencies the list of read dependencies
   *  @param location the location of the original {@code InstructionElement}
   */
  public Instruction(Method method, List<Argument> actualArguments,
                     Set<CommunicationPort> readDependencies, Location location)
  {
    this.method = method;

    this.actualArguments = actualArguments;

    this.readDependencies = readDependencies;

    this.location = location;
  }

  /**
   *  Gets the method containing the executable code of this instruction.
   *
   *  @return the method backing this instruction
   */
  public Method getMethod() {
    return method;
  }

  /**
   *  Gets the actual arguments that will be passed to the method.
   *
   *  @return the list of actual arguments
   */
  public List<Argument> getActualArguments() {
    return actualArguments;
  }

  /**
   *  Gets the read dependencies.
   *
   *  @return the read dependencies
   */
  public Set<CommunicationPort> getReadDependencies() {
    return readDependencies;
  }

  /**
   *  Gets the location of the {@link hu.progtech.cd2t100.asm.InstructionElement}
   *  this instance is constructed from.
   *
   *  @return the location
   */
  public Location getLocation() {
    return location;
  }

  @Override
  public String toString() {
    return method.getName() + " " + actualArguments.toString();
  }
}
