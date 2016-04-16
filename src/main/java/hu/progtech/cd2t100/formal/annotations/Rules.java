package hu.progtech.cd2t100.formal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the preprocessor rules used by a Groovy instruction class.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rules {
  /**
   *  Returns an array of preprocessor rule names.
   *
   *  @return an array of preprocessor rule names
   */
  String[] value();
}
