package hu.progtech.cd2t100.game.model;

import java.io.InputStream;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  {@code InstructionDescriptorDaoXml} is able to deserialize {@code InstructionDescriptor}
 *  instances from an XML source.
 */
public class InstructionDescriptorDaoXml implements InstructionDescriptorDao {
  private static final Logger logger =
    LoggerFactory.getLogger(InstructionDescriptorDaoXml.class);

  private final String xmlFile;

  /**
   *  Constructs a new {@code InstructionDescriptorDaoXml} that will
   *  use the specified XML file as a source.
   *
   *  @param xmlFile the XML file
   */
  public InstructionDescriptorDaoXml(String xmlFile) {
    this.xmlFile = xmlFile;

    logger.info("Backing XML file is {}.", xmlFile);
  }

  /**
   *  Gets the list of available {@code InstructionDescriptor}s. If the
   *  XML file cannot be opened or invalid the method returns {@code null}.
   *
   *  @return the list of {@code InstructionDescriptor}s or {@code null}
   */
  public List<InstructionDescriptor> getAllInstructionDescriptors() {
    try {
      JAXBContext ctx = JAXBContext.newInstance(InstructionDescriptors.class);

      Unmarshaller unmarshaller = ctx.createUnmarshaller();

      InputStream is =
        this.getClass().getClassLoader().getResourceAsStream(xmlFile);

      if (is == null) {
        return null;
      }

      InstructionDescriptors descriptors =
        (InstructionDescriptors)unmarshaller.unmarshal(is);

      return descriptors.getDescriptorList();
    } catch (JAXBException e) {
      logger.error("During unmarshalling: {}", e.getMessage());

      return null;
    }
  }
}
