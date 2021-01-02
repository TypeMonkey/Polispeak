package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;
import jg.ps.parser.nodes.atoms.Type;

public class IllegalHoldException extends LegislativeException{

  public IllegalHoldException(String varName,
                              Type declaredType,
                              Type givenType,
                              int line, 
                              int column, 
                              String billName) {
    super("The hold designated as '"+varName+"' must hold "+declaredType.getTypeName()+
          " only, yet it's given a "+givenType.getTypeName(), line, column, billName);
  }

}
