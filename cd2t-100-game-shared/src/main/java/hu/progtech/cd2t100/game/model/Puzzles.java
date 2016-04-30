package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Container object for a list of {@code Puzzle}s. Introduced
 *  as a helper class for JAXB to represent the root element of the XML file
 *  for {@code Puzzle}s.
 */
@XmlRootElement(name = "puzzles")
class Puzzles {
  private List<Puzzle> puzzleList;

  /**
   *  Gets the list of {@code Puzzle}s.
   *
   *  @return the list
   */
  @XmlElement(name = "puzzle")
  public List<Puzzle> getPuzzleList() {
    return puzzleList;
  }

  /**
   *  Sets the list of puzzles.
   *
   *  @param puzzleList the list
   */
  public void setPuzzleList(List<Puzzle> puzzleList) {
    this.puzzleList = puzzleList;
  }
}
