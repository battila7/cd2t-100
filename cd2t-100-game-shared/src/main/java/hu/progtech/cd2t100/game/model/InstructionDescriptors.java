package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "instructionDescriptors")
class InstructionDescriptors {
  private List<InstructionDescriptor> descriptorList;

  @XmlElement(name = "instructionDescriptor")
  public List<InstructionDescriptor> getDescriptorList() {
    return descriptorList;
  }

  public void setDescriptorList(List<InstructionDescriptor> descriptorList) {
    this.descriptorList = descriptorList;
  }
}
