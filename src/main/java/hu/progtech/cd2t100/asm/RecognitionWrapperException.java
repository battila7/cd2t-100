package hu.progtech.cd2t100.asm;

import java.util.Optional;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.RecognitionException;

/**
 * This exception type is used when rule recognition fails because of an
 * unexpected symbol in the source code.
 * <pre>
 * {@code
 *  MOV 10: ACC
 * }
 * </pre>
 * Recognition fails because of the colon since it can only be used when
 * introducing a new label.
 */
public final class RecognitionWrapperException extends LineNumberedException {
  private RecognitionException wrappedException;

  public RecognitionWrapperException(Location location, RecognitionException exception) {
    super(location);

    this.wrappedException = exception;
  }

  public RecognitionException getWrappedException() {
    return wrappedException;
  }

  @Override
  public String getMessage() {
    String msg = super.getMessage();

    Optional<Token> token =
      Optional.ofNullable(wrappedException.getOffendingToken());

    if (token.isPresent()) {
      msg += "Unexpected symbol. Expected "
           + wrappedException.getExpectedTokens()
             .toString(wrappedException.getRecognizer().getVocabulary())
           + ".";
    } else {
      msg += "Unexpected symbol. Please check your syntax.";
    }

    return msg;
  }
}
