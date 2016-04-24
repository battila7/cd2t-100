package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name="portNameMapping")
public class PortNameMapping {
  private String localName;

  private String globalName;

  @XmlAttribute(name = "local")
  public String getLocalName() {
    return localName;
  }

  public void setLocalName(String localName) {
    this.localName = localName;
  }

  @XmlAttribute(name = "global")
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @Override
  public String toString() {
    return globalName + " as " + localName;
  }
}
