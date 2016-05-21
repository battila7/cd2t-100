package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

/**
 *  {@code InputPortDescriptor} represents an
 *  {@link hu.progtech.cd2t100.computation.io.InputPort} with its
 *  global name and contents. Can be used to serialize or deserialize
 *  {@code InputPort} objects.
 */
@XmlRootElement(name="inputPortDescriptor")
public class InputPortDescriptor {
  private String globalName;

  private List<Integer> contents;

  /**
   *  Gets the global name.
   *
   *  @return the global name
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

  /**
   *  Gets the contents of the described port.
   *
   *  @return the contents
   */
  @XmlElement(name = "contents")
  @XmlList
  public List<Integer> getContents() {
    return contents;
  }

  /**
   *  Sets the contents.
   *
   *  @param contents the contents
   */
  public void setContents(List<Integer> contents) {
    this.contents = contents;
  }

  @Override
  public String toString() {
    return globalName + " provided input " + contents.toString();
  }
}
