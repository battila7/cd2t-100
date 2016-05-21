package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *  {@code CommunicationPortDescriptor} can be used as a basis to
 *  to instantiate {@link hu.progtech.cd2t100.computation.io.CommunicationPort}
 *  objects. It only contains the global name of the port, the
 *  endpoints must be retrieved from the {@code NodeDescriptor}s.
 */
@XmlRootElement(name = "communicationPortDescriptor")
public class CommunicationPortDescriptor {
  private String globalName;

  /**
   *  Gets the global name.
   *
   *  @return the global
   */
  @XmlAttribute
  public String getGlobalName() {
    return globalName;
  }

  /**
   *  Sets the global name.
   *
   *  @param globalName the global name
   */
  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @Override
  public String toString() {
    return globalName;
  }
}
