package hu.progtech.cd2t100.game.model;

import java.util.List;

public interface PuzzleDao {
  List<Puzzle> getAllPuzzles();

  Puzzle getPuzzleByName(String name);
}
