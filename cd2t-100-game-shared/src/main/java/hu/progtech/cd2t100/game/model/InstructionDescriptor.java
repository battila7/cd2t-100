package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class InstructionDescriptor {
  private String opcode;

  private String description;

  private String groovyFile;

  public String getOpcode() {
    return opcode;
  }

  public String getDescription() {
    return description;
  }

  public String getGroovyFile() {
    return groovyFile;
  }

  public void setOpcode(String opcode) {
    this.opcode = opcode;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setGroovyFile(String groovyFile) {
    this.groovyFile = groovyFile;
  }

  @Override
  public String toString() {
    String str = opcode + " (" + groovyFile + ")\n";

    str += description;

    return str;
  }
}
