package hu.progtech.cd2t100.game.model;

import java.util.List;

/**
 *  A common interface for DAO classes that can act as a
 *  source of {@code Puzzle} objects.
 */
public interface PuzzleDao {
  /**
   *  Gets the list of avalilable {@code Puzzle}s.
   *
   *  @return the list of {@code Puzzle}s
   */
  List<Puzzle> getAllPuzzles();

  /**
   *  Gets the {@code Puzzle} with the specified name.
   *
   *  @param name the name of the {@code Puzzle}
   *
   *  @return the puzzle
   */
  Puzzle getPuzzleByName(String name);
}
