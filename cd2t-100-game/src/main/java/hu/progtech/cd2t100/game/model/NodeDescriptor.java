package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "node")
public class NodeDescriptor {
  private String globalName;

  private int maximumSourceCodeLines;

  private int row;

  private int column;

  private List<RegisterDescriptor> registerDescriptors;

  @XmlAttribute
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

  @XmlElement(name = "registers")
  public List<RegisterDescriptor> getRegisterDescriptors() {
    return registerDescriptors;
  }

  public void setRegisterDescriptors(List<RegisterDescriptor> lst) {
    registerDescriptors = lst;
  }
}
