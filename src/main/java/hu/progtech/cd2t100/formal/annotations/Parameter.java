package hu.progtech.cd2t100.formal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import hu.progtech.cd2t100.formal.ParameterType;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
  ParameterType parameterType();

  String implicitValue() default "";
}
