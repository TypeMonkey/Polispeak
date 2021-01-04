package jg.ps.common;

import jg.ps.common.precedent.PrecSection;
import jg.ps.common.precedent.Precedent;
import jg.ps.enforcement.instances.Instance;
import jg.ps.enforcement.instances.StringInstance;
import jg.ps.enforcement.instances.primitives.FloatInstance;
import jg.ps.enforcement.instances.primitives.IntValue;
import jg.ps.enforcement.instances.primitives.NullInstance;

@Precedent(title = "The Bill of Strings",
           desc = "A collection of essential procedures to manipulate and characterize String objects")
public class BillOfStrings {

  @PrecSection(num = 1,
      title = "The Concatenation Law",
      provisions = {"$String", "$String"},
      fulfillment = "$String")
  public static Instance concatenation(Instance left, Instance right) {
    StringInstance leftStr = (StringInstance) left;
    StringInstance rightStr = (StringInstance) right;

    return new StringInstance(leftStr.getValue() + rightStr.getValue());
  }

  @PrecSection(num = 2,
      title = "The Substring Law",
      provisions = {"$String", "$Integer", "$Integer"},
      fulfillment = "$String")
  public static Instance substring(Instance target, Instance start, Instance end) {
    StringInstance targetString = (StringInstance) target;
    
    int sIndex = ((IntValue) start).getValue().intValue();
    int eIndex = ((IntValue) end).getValue().intValue();
    
    return new StringInstance(targetString.getValue().substring(sIndex, eIndex));
  }
  
  @PrecSection(num = 3,
      title = "The Substring Existence Law",
      provisions = {"$String", "$String"},
      fulfillment = "$Integer")
  public static Instance substringExistence(Instance target, Instance toFind) {
    StringInstance tStr = (StringInstance) target;
    StringInstance fStr = (StringInstance) toFind;

    long result = tStr.getValue().contains(fStr.getValue()) ? 1 : 0;
    
    return new IntValue(result);
  }
  
  @PrecSection(num = 4,
      title = "The Character Law",
      provisions = {"$String", "$Integer"},
      fulfillment = "$String")
  public static Instance substring(Instance target, Instance index) {
    StringInstance targetString = (StringInstance) target;        
    IntValue charIndex = (IntValue) index;

    long targetSize = targetString.getValue().length();
    long i = charIndex.getValue();
    
    if (i >= 0 && i < targetSize) {
      return new StringInstance(String.valueOf(targetString.getValue().charAt((int) i)));
    }
    else {
      return NullInstance.getNullInstance();
    }       
  }
  
  @PrecSection(num = 5,
      title = "The Emptiness Law",
      provisions = {"$String"},
      fulfillment = "$Integer")
  public static Instance empty(Instance target) {
    StringInstance str = (StringInstance) target;        

    long result = str.getValue().length() == 0 ? 1 : 0;
    return new IntValue(result);
  }
  
  @PrecSection(num = 6,
      title = "The Transformation Law",
      provisions = {"$Integer"},
      fulfillment = "$String")
  public static Instance transformInt(Instance target) {
    IntValue integer = (IntValue) target;        

    return new StringInstance(String.valueOf(integer.getValue()));
  }
  
  @PrecSection(num = 7,
      title = "The Transformation Law",
      provisions = {"$Float"},
      fulfillment = "$String")
  public static Instance transformFloat(Instance target) {
    FloatInstance floatValue = (FloatInstance) target;        

    return new StringInstance(String.valueOf(floatValue.getValue()));
  }
}
