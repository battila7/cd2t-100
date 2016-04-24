package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "communicationPortDescriptor")
public class CommunicationPortDescriptor {
  private String globalName;

  @XmlAttribute
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @Override
  public String toString() {
    return globalName;
  }
}
