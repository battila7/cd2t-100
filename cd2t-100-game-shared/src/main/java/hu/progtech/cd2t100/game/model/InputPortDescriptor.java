package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

@XmlRootElement(name="inputPortDescriptor")
public class InputPortDescriptor {
  private String globalName;

  private int[] contents;

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
  public int[] getContents() {
    return contents;
  }

  public void setContents(int[] contents) {
    this.contents = contents;
  }
}
