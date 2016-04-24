package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

@XmlRootElement(name="inputPortDescriptor")
public class InputPortDescriptor {
  private String globalName;

  private List<Integer> contents;

  @XmlAttribute
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @XmlElement(name = "contents")
  @XmlList
  public List<Integer> getContents() {
    return contents;
  }

  public void setContents(List<Integer> contents) {
    this.contents = contents;
  }

  @Override
  public String toString() {
    return globalName + " provided input " + contents.toString();
  }
}
