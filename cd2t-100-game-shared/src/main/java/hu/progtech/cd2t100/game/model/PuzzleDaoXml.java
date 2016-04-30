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
 *  {@code PuzzleDaoXml} is able to deserialize {@code Puzzle}
 *  instances from an XML source.
 */
public class PuzzleDaoXml implements PuzzleDao {
  private static final Logger logger =
    LoggerFactory.getLogger(PuzzleDaoXml.class);

  private final String xmlFile;

  private List<Puzzle> puzzleList;

  /**
   *  Constructs a new {@code PuzzleDaoXml} that will
   *  use the specified XML file as a source.
   *
   *  @param xmlFile the XML file
   */
  public PuzzleDaoXml(String xmlFile) {
    this.xmlFile = xmlFile;

    logger.info("Backing XML file is {}.", xmlFile);
  }

  /**
   *  Gets the list of available {@code Puzzle}s. If the
   *  XML file cannot be opened or invalid the method returns {@code null}.
   *
   *  @return the list of {@code Puzzle}s or {@code null}
   */
  public List<Puzzle> getAllPuzzles() {
    try {
      JAXBContext ctx = JAXBContext.newInstance(Puzzles.class);

      Unmarshaller unmarshaller = ctx.createUnmarshaller();

      InputStream is =
        this.getClass().getClassLoader().getResourceAsStream(xmlFile);

      if (is == null) {
        return null;
      }

      Puzzles p =
        (Puzzles)unmarshaller.unmarshal(is);

      puzzleList = p.getPuzzleList();

      return puzzleList;
    } catch (JAXBException e) {
      logger.error("During unmarshalling: {}", e.getMessage());

      return null;
    }
  }

  /**
   *  Gets the {@code Puzzle} with the specified name or {@code null}
   *  if it cannot be found.
   *
   *  @param name the name of the {@code Puzzle}
   *
   *  @return the {@code Puzzle} with the specified name or {@code null}
   */
  public Puzzle getPuzzleByName(String name) {
    /*
     *  Call getAllPuzzles just for the side-effect.
     */
    if ((puzzleList == null) && (getAllPuzzles() == null)) {
      return null;
    }

    for (Puzzle puzzle : puzzleList) {
      if (puzzle.getName() == name) {
        return puzzle;
      }
    }

    return null;
  }
}
