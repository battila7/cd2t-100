package hu.progtech.cd2t100.formal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import hu.progtech.cd2t100.formal.ParameterType;

/**
 *  Indicates the type and the implicit value of an {@code apply} method's
 *  formal parameter in a Groovy instruction class.
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
