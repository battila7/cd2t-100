package hu.progtech.cd2t100.computation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import hu.progtech.cd2t100.computation.ArgumentType;
import hu.progtech.cd2t100.computation.PortAccess;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
  ArgumentType argumentType();

  PortAccess portAccess() default PortAccess.READ;

  String implicitValue();
}
