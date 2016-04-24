package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name="nodeDescriptor")
public class NodeDescriptor {
  private String globalName;

  private int maximumSourceCodeLines;

  private int row;

  private int column;

  private List<RegisterDescriptor> registerDescriptors;

  private List<PortNameMapping> readablePorts;

  private List<PortNameMapping> writeablePorts;

  @XmlAttribute(name="globalName")
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @XmlAttribute(name = "maxLines")
  public int getMaximumSourceCodeLines() {
    return maximumSourceCodeLines;
  }

  public void setMaximumSourceCodeLines(int max) {
    maximumSourceCodeLines = max;
  }

  @XmlAttribute(name = "row")
  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  @XmlAttribute(name = "column")
  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  @XmlElement(name="registerDescriptor", type=RegisterDescriptor.class)
  @XmlElementWrapper(name="registers")
  public List<RegisterDescriptor> getRegisterDescriptors() {
    return registerDescriptors;
  }

  public void setRegisterDescriptors(List<RegisterDescriptor> lst) {
    registerDescriptors = lst;
  }

  @XmlElement(name="portNameMapping", type=PortNameMapping.class)
  @XmlElementWrapper(name="readablePorts")
  public List<PortNameMapping> getReadablePorts() {
    return readablePorts;
  }

  public void setReadablePorts(List<PortNameMapping> ports) {
    this.readablePorts = ports;
  }

  @XmlElement(name="portNameMapping", type=PortNameMapping.class)
  @XmlElementWrapper(name="writeablePorts")
  public List<PortNameMapping> getWriteablePorts() {
    return writeablePorts;
  }

  public void setWriteablePorts(List<PortNameMapping> ports) {
    this.writeablePorts = ports;
  }

  @Override
  public String toString() {
    String ret = globalName + ", max: " + maximumSourceCodeLines + "\n";

    ret += row + " " + column + "\n";

    if (registerDescriptors != null) {
      ret += registerDescriptors.toString() + "\n";
    }

    if (readablePorts != null) {
      ret += readablePorts.toString() + "\n";
    }

    if (writeablePorts != null) {
      ret += writeablePorts.toString() + "\n";
    }

    return ret;
  }
}
