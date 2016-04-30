package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *  {@code NodeDescriptor} is the model class that stores
 *  data describing {@link hu.progtech.cd2t100.computation.Node} instances.
 *  Can be used to serialize or deserialize {@code Node}s.
 */
@XmlRootElement(name="nodeDescriptor")
public class NodeDescriptor {
  private String globalName;

  private int maximumSourceCodeLines;

  private int row;

  private int column;

  private List<RegisterDescriptor> registerDescriptors;

  private List<PortNameMapping> readablePorts;

  private List<PortNameMapping> writeablePorts;

  /**
   *  Gets the global name.
   *
   *  @return the global name
   */
  @XmlAttribute(name="globalName")
  public String getGlobalName() {
    return globalName;
  }

  /**
   *  Sets the global name.
   *
   *  @param globalName the global name
   */
  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  /**
   *  Gets the maximum number of source code lines.
   *
   *  @return the maximum
   */
  @XmlAttribute(name = "maxLines")
  public int getMaximumSourceCodeLines() {
    return maximumSourceCodeLines;
  }

  /**
   *  Sets the maximum number of source code lines.
   *
   *  @param max the maximum
   */
  public void setMaximumSourceCodeLines(int max) {
    maximumSourceCodeLines = max;
  }

  /**
   *  Gets the row component of the {@code Node}'s position.
   *
   *  @return the row
   */
  @XmlAttribute(name = "row")
  public int getRow() {
    return row;
  }

  /**
   *  Sets the row component of the {@code Node}'s position.
   *
   *  @param row the row
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   *  Gets the column component of the {@code Node}'s position.
   *
   *  @return the column
   */
  @XmlAttribute(name = "column")
  public int getColumn() {
    return column;
  }

  /**
   *  Sets the column component of the {@code Node}'s position.
   *
   *  @param column the column
   */
  public void setColumn(int column) {
    this.column = column;
  }

  /**
   *  Gets the list of {@code RegisterDescriptor}s describing the
   *  {@code Node}'s registers.
   *
   *  @return the list of {@code RegisterDescriptor}s
   */
  @XmlElement(name="registerDescriptor", type=RegisterDescriptor.class)
  @XmlElementWrapper(name="registers")
  public List<RegisterDescriptor> getRegisterDescriptors() {
    return registerDescriptors;
  }

  /**
   *  Sets the list of {@code RegisterDescriptor}s.
   *
   *  @param list the list
   */
  public void setRegisterDescriptors(List<RegisterDescriptor> list) {
    registerDescriptors = list;
  }

  /**
   *  Gets the readable ports of the described {@code Node} instance.
   *
   *  @return the readable ports
   */
  @XmlElement(name="portNameMapping", type=PortNameMapping.class)
  @XmlElementWrapper(name="readablePorts")
  public List<PortNameMapping> getReadablePorts() {
    return readablePorts;
  }

  /**
   *  Sets the readable ports of the described {@code Node} instance.
   *
   *  @param ports the readable ports
   */
  public void setReadablePorts(List<PortNameMapping> ports) {
    this.readablePorts = ports;
  }

  /**
   *  Gets the writeable ports of the described {@code Node} instance.
   *
   *  @return the writeable ports
   */
  @XmlElement(name="portNameMapping", type=PortNameMapping.class)
  @XmlElementWrapper(name="writeablePorts")
  public List<PortNameMapping> getWriteablePorts() {
    return writeablePorts;
  }

  /**
   *  Sets the writeable ports of the described {@code Node} instance.
   *
   *  @param ports the writeable ports
   */
  public void setWriteablePorts(List<PortNameMapping> ports) {
    this.writeablePorts = ports;
  }

  @Override
  public String toString() {
    String ret = globalName + "\nMaximum number of source code lines: "
               + maximumSourceCodeLines + "\n";

    ret += "Registers:\n";

    if (registerDescriptors != null) {
      ret += registerDescriptors.toString() + "\n";
    }

    ret += "Readable Ports:\n";
    if (readablePorts != null) {
      ret += readablePorts.toString() + "\n";
    }

    ret += "Writeable Ports:\n";

    if (writeablePorts != null) {
      ret += writeablePorts.toString() + "\n";
    }

    return ret;
  }
}
