package jg.ps.common.precedent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for precedent definitions.
 * 
 * The name of the implementing class is the name of this definition
 * and will be used to refer to it in other legislation.
 * 
 * The actual class this definition corresponds should be
 * Plain-Old-Java-Object (POJO) with the additional requirements:
 * 
 *  - The class and it's sole constructor must be public
 *  
 *  - The class is a static class in the class that represents the whole precedent
 * 
 *  - All fields that are meant to be definition members (has the @PrecMember annotation)
 *    should be non-static.
 *    
 *  - All fields must be of the type jg.ps.enforcement.instances.Instance
 *  
 *  - There's no more (and no less) than one constructor with the same amount of 
 *    parameters as there are members.
 *    * In spirit of the functional nature of Polispeak, this constructor should do 
 *      no more than initialize its members with the given arguments, but ultimately,
 *      this is at the discretion of the developer. (Please be kind.)
 * 
 * @author Jose Guaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrecDefinition {}
