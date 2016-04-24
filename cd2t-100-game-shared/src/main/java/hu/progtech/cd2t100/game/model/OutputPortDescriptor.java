package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

@XmlRootElement(name = "outputPortDescriptor")
public class OutputPortDescriptor {
  private String globalName;

  private List<Integer> expectedContents;

  @XmlAttribute
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @XmlElement(name="expected")
  @XmlList
  public List<Integer> getExpectedContents() {
    return expectedContents;
  }

  public void setExpectedContents(List<Integer> expectedContents) {
    this.expectedContents = expectedContents;
  }

  @Override
  public String toString() {
    return globalName + " expected output " + expectedContents.toString();
  }
}
