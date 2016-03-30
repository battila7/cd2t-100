package hu.progtech.cd2t100.asm;

/**
 * This exception type is used when a label name collides with an
 * existing register or port name. For example (assuming {@code ACC} is
 * an existing register):
 * <pre>
 * {@code
 *  ACC:
 *    MOV 10 ACC
 * }
 * </pre>
 * When processing this code, it's not possible to determine the type of
 * {@code MOV}'s second argument, because it references both the register
 * and the port.
 */
public final class LabelNameCollisionException extends LineNumberedException {
  private String labelName;

  public LabelNameCollisionException(Location location, String labelName) {
    super(location);

    this.labelName = labelName;
  }

  public String getLabelName() {
    return labelName;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
           + "Label with name \"" + labelName
           + "\" collides with existing port or register name.";
  }
}
