package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

@XmlRootElement(name = "outputPort")
public class OutputPortDescriptor {
  private String globalName;

  private int[] expectedContents;

  private String writeableByNode;

  @XmlAttribute
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @XmlElement(name = "writeableBy")
  public String getWriteableByNode() {
    return writeableByNode;
  }

  public void setWriteableByNode(String node) {
    writeableByNode = node;
  }

  @XmlElement(name = "expected")
  @XmlList
  public int[] getExpectedContens() {
    return expectedContents;
  }

  public void setExpectedContents(int[] expectedContents) {
    this.expectedContents = expectedContents;
  }
}
