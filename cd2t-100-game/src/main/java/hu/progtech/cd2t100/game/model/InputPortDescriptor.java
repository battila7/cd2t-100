package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

@XmlRootElement(name = "InputPort")
public class InputPortDescriptor {
  private String globalName;

  private List<Integer> contents;

  private String readableByNode;

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

  @XmlElement(name = "contents")
  @XmlList
  public List<Integer> getContens() {
    return contents;
  }

  public void setContents(List<Integer> contents) {
    this.contents = contents;
  }
}
