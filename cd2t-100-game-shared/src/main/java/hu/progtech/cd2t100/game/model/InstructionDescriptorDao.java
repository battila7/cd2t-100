package hu.progtech.cd2t100.game.model;

import java.util.List;

/**
 *  A common interface for DAO classes that can act as a
 *  source of {@code InstructionDescriptor} objects.
 */
public interface InstructionDescriptorDao {
  /**
   *  Gets the list of available {@code InstructionDescriptor}s.
   *
   *  @return the list
   */
  List<InstructionDescriptor> getAllInstructionDescriptors();
}
