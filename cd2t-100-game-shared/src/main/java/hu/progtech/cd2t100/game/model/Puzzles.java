package hu.progtech.cd2t100.game.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "puzzles")
class Puzzles {
  private List<Puzzle> puzzleList;

  @XmlElement(name = "puzzle")
  public List<Puzzle> getPuzzleList() {
    return puzzleList;
  }

  public void setPuzzleList(List<Puzzle> puzzleList) {
    this.puzzleList = puzzleList;
  }
}
