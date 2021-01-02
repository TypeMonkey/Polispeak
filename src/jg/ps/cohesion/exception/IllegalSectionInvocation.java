package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;
import jg.ps.parser.nodes.atoms.Type;

public class IllegalSectionInvocation extends LegislativeException{

  public IllegalSectionInvocation(int secNumber, 
                                  String secBill,  
                                  int expectedProvisions,
                                  int givenPorivisions,
                                  int line, 
                                  int column, 
                                  String billName) {
    super("Invoking Section "+secBill+" of "+secBill+" requires "+expectedProvisions+" provisions " +
          "but the invocation only provides "+givenPorivisions+" provisions", line, column, billName);
  }

  public IllegalSectionInvocation(int secNumber, 
                                  String secBill,  
                                  int provisionNumber,
                                  Type expectedType,
                                  Type given,
                                  int line, 
                                  int column, 
                                  String billName) {
    super("The "+correctIndex(provisionNumber)+" provision of Section "+secNumber+" of "+secBill+
          "must be a "+expectedType.getTypeName()+" but was provided a "+given, line, column, billName);
  }
  
  public static String correctIndex(int index) {
    switch (index) {
    case 1:
      return "st";
    case 2:
      return "nd";
    case 3:
      return "rd";
    default:
      return "th";
    }
  }
}
