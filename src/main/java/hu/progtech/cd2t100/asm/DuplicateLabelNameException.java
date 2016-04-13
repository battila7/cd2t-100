package hu.progtech.cd2t100.asm;

/**
 * This exception type is used when a label name is used twice (or more)
 * in the same source code. For example:
 * <pre>
 * {@code
 *  start:
 *    MOV 10 ACC
 *  start:
 *    ADD -1
 *    JNZ start
 * }
 * </pre>
 * Here {@code start} is duplicated and makes the {@code JNZ start} jump
 * ambiguous.
 */
public final class DuplicateLabelNameException extends LineNumberedException {
  private String labelName;

  /**
   * Constructs a new {@code DuplicateLabelNameException}
   * with the specified location and the duplicated label name.
   *
   * @param location the location of duplication's occurence
   * @param labelName the name of the duplicated label
   */
  public DuplicateLabelNameException(Location location, String labelName) {
    super(location);

    this.labelName = labelName;
  }

  /**
   *  Returns the name of the label causing the exception.
   *
   *  @return the name of the label
   */
  public String getLabelName() {
    return labelName;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Label with name \"" + labelName
           + "\" already exists.";
  }
}
