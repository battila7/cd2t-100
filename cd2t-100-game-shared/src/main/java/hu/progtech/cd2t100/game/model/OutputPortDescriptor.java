package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;

/**
 *  {@code OutputPortDescriptor} is the representation of an
 *  {@link hu.progtech.cd2t100.computation.io.OutputPort} with its
 *  global name and expected contents.
 */
@XmlRootElement(name = "outputPortDescriptor")
public class OutputPortDescriptor {
  private String globalName;

  private List<Integer> expectedContents;

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
   *  Gets the expected contents of the described port.
   *
   *  @return the expected contents
   */
  @XmlElement(name="expected")
  @XmlList
  public List<Integer> getExpectedContents() {
    return expectedContents;
  }

  /**
   *  Sets the expected contents.
   *
   *  @param expectedContents the expected contents
   */
  public void setExpectedContents(List<Integer> expectedContents) {
    this.expectedContents = expectedContents;
  }

  @Override
  public String toString() {
    return globalName + " expected output " + expectedContents.toString();
  }
}
