package jg.ps.common.precedent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for precedent sections
 * 
 * The methods which such sections correspond must have the given form:
 *  
 *  - Static and public
 *  - Parameters must be of the type jg.ps.enforcement.instances.Instance
 *  - Return type must also be of the type jg.ps.enforcement.instances.Instance
 *    (even if the method doesn't actually return anything - i.e: void. Such methods can return null
 *     and it will be ignored)
 *     
 * Note: Precedent section numbers must be >= 1 and <= # of total sections in the legislation
 * 
 * @author Jose Guaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PrecSection {

  /**
   * The number that uniquely identifies this section
   */
  int num();
  
  /**
   * The title of this section. 
   * Can be omitted. 
   */
  String title() default "";
  
  /**
   * The types of the provisions of this section, listed in order of expectation.
   * 
   * For example, if this section expects An Integer as its first provision, and A String as its second,
   * then Integer should be listed first, and then String
   * 
   * If a type is listed in it's simple form - ex: "Integer" instead of "The Constitution$Integer" - 
   * then it's assumed the type being referred is within the host legislation of this bill.
   * 
   * For shorthand, if you'd like a type to refer to one in "The Constitution", then simply
   * use "$<typename>", ex: "$Integer" will be interpreted as "The Constitution$Integer"
   */
  String[] provisions() default {};
  
  /**
   * The type of the object produced by fulfilling this section.
   * 
   * If this section is omitted, it's assumed that this section produces no object.
   *  - Not the null object! No object at all.
   * 
   * If a type is listed in it's simple form - ex: "Integer" instead of "The Constitution$Integer" - 
   * then it's assumed the type being referred is within the host legislation of this bill.
   * 
   * For shorthand, if you'd like a type to refer to one in "The Constitution", then simply
   * use "$<typename>", ex: "$Integer" will be interpreted as "The Constitution$Integer"
   */
  String fulfillment() default "The Constitution$Void";
  
}
