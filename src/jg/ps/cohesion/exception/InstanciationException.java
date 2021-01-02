package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;
import jg.ps.parser.nodes.atoms.Type;

public class InstanciationException extends LegislativeException{

  public InstanciationException(String defName, 
      String hostBill,
      int expectedProvisions,
      int givenPorivisions,
      int line, 
      int column, 
      String billName) {
    super("Instantiating definition '"+defName+"' of "+hostBill+" requires "+expectedProvisions+" provisions " +
        "but the invocation only provides "+givenPorivisions+" provisions", line, column, billName);
  }

  public InstanciationException(String defName, 
      String hostBill,
      int provisionNumber,
      Type expectedType,
      Type given,
      int line, 
      int column, 
      String billName) {
    super("The "+IllegalSectionInvocation.correctIndex(provisionNumber)+
          " of the definition of a '"+defName+"' in "+hostBill+
          "must be a "+expectedType.getTypeName()+" but we provided a "+given, line, column, billName);
  }

}
