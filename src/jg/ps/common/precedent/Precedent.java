package jg.ps.common.precedent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * In Polispeak, A precedent (a.k.a "founding legislation") is a law detailing
 * inherent procedures/rights/laws that all legislation "build up" from and abide by - if applicable.
 * 
 * There will be some procedures that cannot be directly described using Polispeak. 
 * So, creating a new precedent to better freely describe it may be useful.
 * 
 * In software development, Polispeak precedents are equivalent to writing low-level libraries
 * for a application - written in a high-level language - to us.
 * 
 *  Ex: Without proper access to the Unsafe Java API (which previously allowed for low-level memory access),
 *      If a Java project requires direct memory manipulation - like checking heap addresses, etc. - a developer
 *      may choose to develop a small C API as C has low-level facilities in which they can use in their
 *      Java project (perhaps through something like JNI?).
 *      
 * Note: A precedent can only utilize types that's within itself or inside other precedents
 * 
 * Example use of the Precedent annotations:
 * 
 * @Precedent(title="Basic Procedures for Voting")
 * public class Voting{
 * 
 *    //The @PrecDefinition has more precise guidelines and rules in designing definitions
 *    @PrecDefinition
 *    public static class Voter{
 *    
 *      @PrecMember(type="The Constitution$String")
 *      Instance name;  //Has to be of type jg.ps.enforcement.instances.Instance and non-static
 *      
 *      @PrecMember(type="The Constitution$String")
 *      Instance affiliation;
 *      
 *      public Voter(Instance name, Instance affiliation){
 *        this.name = name;
 *        this.affiliation = affiliation;
 *      }
 *    }
 *    
 *    @PrecSection(num=0, 
 *                 title="Concatenation",
 *                 provisions={"Voter"},
 *                 fulfillment={"The Constitution$String"})
 *    static Instance concatInformation(Instance voter){
 *      //All instances of user-defined definitions (be it through precedent or not)
 *      //are instantiated as DefInstances under the hood
 *      DefInstance actualVoter = (DefInstance) voter;
 *      
 *      StringInstance name = (StringInstance) actualVoter.getMember("name");
 *      StringInstance affiliation = (StringInstance) actualVoter.getMember("affiliation");
 *      
 *      String concatenated = name.getValue()+":"+affiliation.getValue();
 *      return new StringInstance(concatenated);
 *    }
 * 
 * }
 * 
 * @author Jose Guaro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Precedent {

  /**
   * The title of this Precedent.
   * 
   * Should be a short title that briefly
   * describes the precedent's content.
   */
  String title();
  
  /**
   * A detailed description of this precedent,
   */
  String desc();
  
}
