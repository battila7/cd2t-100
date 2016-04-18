package hu.progtech.cd2t100.formal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate which opcode a class is responsible for.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Opcode {
	/**
	 *	The opcode handled by the instruction annotated with this annotation.
	 *
	 *	@return the value of the annotation
	 */
	String value();
}
