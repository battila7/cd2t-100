package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *  {@code PortNameMapping} is a key-value pair that maps node-local
 *  port names to their emulator-global global names.
 */
@XmlRootElement(name="portNameMapping")
public class PortNameMapping {
  private String localName;

  private String globalName;

  /**
   *  Gets the node-local name of the port.
   *
   *  @return the loal name
   */
  @XmlAttribute(name = "local")
  public String getLocalName() {
    return localName;
  }

  /**
   *  Sets the node-local name.
   *
   *  @param localName the local name
   */
  public void setLocalName(String localName) {
    this.localName = localName;
  }

  /**
   *  Gets the emulator-global name of the port.
   *
   *  @return the global name
   */
  @XmlAttribute(name = "global")
  public String getGlobalName() {
    return globalName;
  }

  /**
   *  Sets the emulator-global name.
   *
   *  @param globalName the global name
   */
  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @Override
  public String toString() {
    return globalName + " as " + localName;
  }
}
