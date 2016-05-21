package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import hu.progtech.cd2t100.game.util.TrimmingNormalizerAdapter;

/**
 *  {@code InstructionDescriptor} contains metadata about instructions
 *  along with the path to the Groovy file containing them.
 */
public class InstructionDescriptor {
  private String opcode;

  private String description;

  private String groovyFile;

  /**
   *  Gets the opcode of the described {@code InstructionInfo}.
   *
   *  @return the opcode
   */
  public String getOpcode() {
    return opcode;
  }

  /**
   *  Gets the description of the instruction.
   *
   *  @return the description
   */
  @XmlJavaTypeAdapter(TrimmingNormalizerAdapter.class)
  public String getDescription() {
    return description;
  }

  /**
   *  Gets the path to the Groovy file containing the code.
   *
   *  @return the Groovy file
   */
  public String getGroovyFile() {
    return groovyFile;
  }

  /**
   *  Sets the opcode.
   *
   *  @param opcode the opcode
   */
  public void setOpcode(String opcode) {
    this.opcode = opcode;
  }

  /**
   *  Sets the description.
   *
   *  @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   *  Sets the path to the Groovy file.
   *
   *  @param groovyFile the Groovy file
   */
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
