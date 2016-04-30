package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Container object for a list of {@code InstructionDescriptor}s. Introduced
 *  as a helper class for JAXB to represent the root element of the XML file
 *  for {@code InstructionDescriptor}s.
 */
@XmlRootElement(name = "instructionDescriptors")
class InstructionDescriptors {
  private List<InstructionDescriptor> descriptorList;

  /**
   *  Gets the list of {@code InstructionDescriptor}s.
   *
   *  @return the list
   */
  @XmlElement(name = "instructionDescriptor")
  public List<InstructionDescriptor> getDescriptorList() {
    return descriptorList;
  }

  /**
   *  Sets the list of {@code InstructionDescriptor}s.
   *
   *  @param descriptorList the list
   */
  public void setDescriptorList(List<InstructionDescriptor> descriptorList) {
    this.descriptorList = descriptorList;
  }
}
