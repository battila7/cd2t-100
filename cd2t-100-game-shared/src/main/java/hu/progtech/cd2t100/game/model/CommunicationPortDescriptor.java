package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "communicationPort")
public class CommunicationPortDescriptor {
  private String globalName;

  private String readableByNode;

  private String writeableByNode;

  @XmlAttribute
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @XmlElement(name = "readableBy")
  public String getReadableByNode() {
    return readableByNode;
  }

  public void setReadableByNode(String node) {
    readableByNode = node;
  }

  @XmlElement(name = "writeableBy")
  public String getWriteableByNode() {
    return writeableByNode;
  }

  public void setWriteableByNode(String node) {
    writeableByNode = node;
  }
}
