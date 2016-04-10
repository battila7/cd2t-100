package hu.progtech.cd2t100.computation;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Set;

import hu.progtech.cd2t100.computation.io.CommunicationPort;

public class Instruction {
  private Method method;

  private List<Argument> actualArguments;

  private Set<CommunicationPort> readDependencies;

  public Instruction(Method method, List<Argument> actualArguments,
                     Set<CommunicationPort> readDependencies)
  {
    this.method = method;

    this.actualArguments = actualArguments;

    this.readDependencies = readDependencies;
  }

  public Method getMethod() {
    return method;
  }

  public List<Argument> getActualArguments() {
    return actualArguments;
  }

  public Set<CommunicationPort> getReadDependencies() {
    return readDependencies;
  }
}
