package hu.progtech.cd2t100.game.model;

import java.io.InputStream;

import java.nio.file.Paths;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstructionDescriptorDaoXml implements InstructionDescriptorDao {
  private static final Logger logger =
    LoggerFactory.getLogger(InstructionDescriptorDaoXml.class);

  private final String xmlFile;

  public InstructionDescriptorDaoXml(String xmlFile) {
    this.xmlFile = xmlFile;

    logger.info("Backing XML file is {}.", xmlFile);
  }

  public List<InstructionDescriptor> getAllInstructionDescriptors() {
    try {
      JAXBContext ctx = JAXBContext.newInstance(InstructionDescriptors.class);

      Unmarshaller unmarshaller = ctx.createUnmarshaller();

      InputStream is =
        this.getClass().getClassLoader().getResourceAsStream(xmlFile);

      InstructionDescriptors descriptors =
        (InstructionDescriptors)unmarshaller.unmarshal(is);

      return descriptors.getDescriptorList();
    } catch (JAXBException e) {
      logger.error("During unmarshalling: {}", e.getMessage());

      return null;
    }
  }
}
