package jg.ps.common.precedent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for a definition's member.
 * 
 * The name of this member is the name of the variable in the field
 * it's applied to.
 * 
 * The actual Java field this member corresponds to should be
 * non-static.
 * 
 * @author Jose Guaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrecMember {

  /**
   * The expected type of this member.
   * 
   * If a type is listed in it's simple form - ex: "Integer" instead of "The Constitution$Integer" - 
   * then it's assumed the type being referred is within the host legislation of this bill.
   * 
   * For shorthand, if you'd like a type to refer to one in "The Constitution", then simply
   * use "$<typename>", ex: "$Integer" will be interpreted as "The Constitution$Integer"
   */
  String type();

}
