package hu.progtech.cd2t100.formal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import hu.progtech.cd2t100.formal.ParameterType;

/**
 *  Indicates the type and implicit value of an {@code apply} methods
 *  formal parameter.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
  /**
   *  Returns the type of the parameter.
   *
   *  @return the type of the parameter
   */
  ParameterType parameterType();

  /**
   *  Returns the implicit value of the parameter.
   *
   *  @return the implicit value
   */
  String implicitValue() default "";
}
