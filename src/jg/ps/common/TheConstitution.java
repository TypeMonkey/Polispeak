package jg.ps.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jg.ps.common.precedent.PrecDefinition;
import jg.ps.common.precedent.PrecMember;
import jg.ps.common.precedent.PrecSection;
import jg.ps.common.precedent.Precedent;
import jg.ps.enforcement.instances.Instance;
import jg.ps.enforcement.instances.StringInstance;
import jg.ps.enforcement.instances.primitives.FloatInstance;
import jg.ps.enforcement.instances.primitives.IntValue;
import jg.ps.enforcement.instances.primitives.NullInstance;

@Precedent(title = "The Constitution",
           desc = "Collection of definitions and sections that lay the foundations of all laws")
public class TheConstitution {

  /**
   * Represents a whole number (positive, negative, or zero) in Polispeak
   * 
   * Internally, they are 64-bit integers.
   */
  @PrecDefinition
  public static class Integer{}
  
  /**
   * Represents a decimal number (positive, negative, or zero) in Polispeak
   * 
   * Internally, they are 64-bit doubles.
   */
  @PrecDefinition
  public static class Float{}
  
  /**
   * Placeholder type that can represent all types.
   * 
   * Sort of like the "Any" 'type' in other languages
   * or Object in Java (discounting inheritance)
   */
  @PrecDefinition
  public static class Unknown{}
  
  /**
   * The type for the null instance.
   */
  @PrecDefinition
  public static class Null{}
  
  /**
   * The type used by void sections
   */
  @PrecDefinition
  public static class Void{} 
  
  /**
   * Represents a sequence of characters
   */
  @PrecDefinition
  public static class String{
    
    @PrecMember(type = "$Integer")
    Instance length;
  }
  
  //ADDITION LAWS---------------
  @PrecSection(num = 1,
      title = "The Addition Law",
      provisions = {"$Integer", "$Integer"},
      fulfillment = "$Integer")
  public static Instance addII(Instance left, Instance right) {
    IntValue leftInt = (IntValue) left;
    IntValue rightInt = (IntValue) right;

    return new IntValue(leftInt.getValue() + rightInt.getValue());
  }
  
  @PrecSection(num = 2,
      title = "The Addition Law",
      provisions = {"$Float", "$Float"},
      fulfillment = "$Float")
  public static Instance addFF(Instance left, Instance right) {
    FloatInstance leftFloat = (FloatInstance) left;
    FloatInstance rightFloat = (FloatInstance) right;

    return new FloatInstance(leftFloat.getValue() + rightFloat.getValue());
  }
  
  @PrecSection(num = 3,
      title = "The Addition Law",
      provisions = {"$Integer", "$Float"},
      fulfillment = "$Float")
  public static Instance addIF(Instance left, Instance right) {
    IntValue leftInt = (IntValue) left;
    FloatInstance rightFloat = (FloatInstance) right;

    return new FloatInstance(leftInt.getValue() + rightFloat.getValue());
  }
  
  @PrecSection(num = 4,
      title = "The Addition Law",
      provisions = {"$Float", "$Integer"},
      fulfillment = "$Float")
  public static Instance addFI(Instance left, Instance right) {
    FloatInstance leftFloat = (FloatInstance) left;
    IntValue rightInt = (IntValue) right;

    return new FloatInstance(leftFloat.getValue() + rightInt.getValue());
  }
  
  //MULTIPLICATION LAWS
  @PrecSection(num = 5,
      title = "The Multiplication Law",
      provisions = {"$Integer", "$Integer"},
      fulfillment = "$Integer")
  public static Instance multII(Instance left, Instance right) {
    IntValue leftInt = (IntValue) left;
    IntValue rightInt = (IntValue) right;

    return new IntValue(leftInt.getValue() * rightInt.getValue());
  }
  
  @PrecSection(num = 6,
      title = "The Multiplication Law",
      provisions = {"$Float", "$Float"},
      fulfillment = "$Float")
  public static Instance multFF(Instance left, Instance right) {
    FloatInstance leftFloat = (FloatInstance) left;
    FloatInstance rightFloat = (FloatInstance) right;

    return new FloatInstance(leftFloat.getValue() * rightFloat.getValue());
  }
  
  @PrecSection(num = 7,
      title = "The Multiplication Law",
      provisions = {"$Integer", "$Float"},
      fulfillment = "$Float")
  public static Instance multIF(Instance left, Instance right) {
    IntValue leftInt = (IntValue) left;
    FloatInstance rightFloat = (FloatInstance) right;

    return new FloatInstance(leftInt.getValue() * rightFloat.getValue());
  }
  
  @PrecSection(num = 8,
      title = "The Multiplication Law",
      provisions = {"$Float", "$Integer"},
      fulfillment = "$Float")
  public static Instance multFI(Instance left, Instance right) {
    FloatInstance leftFloat = (FloatInstance) left;
    IntValue rightInt = (IntValue) right;

    return new FloatInstance(leftFloat.getValue() * rightInt.getValue());
  }
  
  //DIVISION LAWS --------------------
  @PrecSection(num = 9,
      title = "The Division Law",
      provisions = {"$Integer", "$Integer"},
      fulfillment = "$Integer")
  public static Instance divII(Instance left, Instance right) {
    IntValue leftInt = (IntValue) left;
    IntValue rightInt = (IntValue) right;

    return new FloatInstance((double) (leftInt.getValue() / rightInt.getValue()));
  }
  
  @PrecSection(num = 10,
      title = "The Division Law",
      provisions = {"$Float", "$Float"},
      fulfillment = "$Float")
  public static Instance divFF(Instance left, Instance right) {
    FloatInstance leftFloat = (FloatInstance) left;
    FloatInstance rightFloat = (FloatInstance) right;

    return new FloatInstance(leftFloat.getValue() / rightFloat.getValue());
  }
  
  @PrecSection(num = 11,
      title = "The Division Law",
      provisions = {"$Integer", "$Float"},
      fulfillment = "$Float")
  public static Instance divIF(Instance left, Instance right) {
    IntValue leftInt = (IntValue) left;
    FloatInstance rightFloat = (FloatInstance) right;

    return new FloatInstance(leftInt.getValue() / rightFloat.getValue());
  }
  
  @PrecSection(num = 12,
      title = "The Division Law",
      provisions = {"$Float", "$Integer"},
      fulfillment = "$Float")
  public static Instance divFI(Instance left, Instance right) {
    FloatInstance leftFloat = (FloatInstance) left;
    IntValue rightInt = (IntValue) right;

    return new FloatInstance(leftFloat.getValue() / rightInt.getValue());
  }
  
  //NEGATION LAWS--------
  @PrecSection(num = 13,
      title = "The Negation Law",
      provisions = {"$Integer"},
      fulfillment = "$Integer")
  public static Instance divIF(Instance value) {
    IntValue valueInt = (IntValue) value;

    return new IntValue(-valueInt.getValue());
  }
  
  @PrecSection(num = 14,
      title = "The Negation Law",
      provisions = {"$Float"},
      fulfillment = "$Float")
  public static Instance divFI(Instance value) {
    FloatInstance valueFloat = (FloatInstance) value;

    return new FloatInstance(-valueFloat.getValue());
  }
  
  //INTROSPECTION PRINCIPLE--------
  @PrecSection(num = 15,
      title = "Introspection Principle",
      provisions = {"$Unknown"},
      fulfillment = "$String")
  public static Instance introspection(Instance value) {
    return new StringInstance(value.getType());
  }
  
  //EQUALITY PRINCIPLE--------
  @PrecSection(num = 16,
      title = "Equality Principle",
      provisions = {"$Unknown", "$Unknown"},
      fulfillment = "$Integer")
  public static Instance equals(Instance left, Instance right) {  
    long boolResult = left.getType().equals(right.getType()) && left.equals(right) ? 1 : 0;   
    return new IntValue(boolResult);
  }
  
  //COMPARISON PROCEDURES
  @PrecSection(num = 17,
      title = "Negativity Principle",
      provisions = {"$Float"},
      fulfillment = "$Integer")
  public static Instance isNegativeF(Instance value) {
    FloatInstance val = (FloatInstance) value;
    long boolResult = val.getValue() < 0 ? 1 : 0;   
    return new IntValue(boolResult);
  }
  
  @PrecSection(num = 18,
      title = "Negativity Principle",
      provisions = {"$Integer"},
      fulfillment = "$Integer")
  public static Instance isNegativeI(Instance value) {
    IntValue val = (IntValue) value;
    long boolResult = val.getValue() < 0 ? 1 : 0;   
    return new IntValue(boolResult);
  }
  
  //IO PRINCIPLE
  @PrecSection(num = 19,
      title = "The IO Principle",
      fulfillment = "$String")
  public static Instance input() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    java.lang.String input = null;
    try {
      input = reader.readLine();
    } catch (IOException e) {
      System.err.println("----IO ERROR AT INPUT????? ");
    }
            
    return input == null ? NullInstance.getNullInstance() : new StringInstance(input);
  }
  
  //IO PRINCIPLE
  @PrecSection(num = 20,
      title = "The IO Principle",
      provisions = {"$String"},
      fulfillment = "$String")
  public static Instance outputLn(Instance toPrint) {
    StringInstance output = (StringInstance) toPrint; 
    System.out.println(output.getValue()); 
    return output;
  }
  
  //IO PRINCIPLE
  @PrecSection(num = 21,
      title = "The IO Principle",
      provisions = {"$String"},
      fulfillment = "$String")
  public static Instance output(Instance toPrint) {
    StringInstance output = (StringInstance) toPrint; 
    System.out.print(output.getValue()); 
    return output;
  }
}
