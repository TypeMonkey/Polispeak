package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;
import jg.ps.parser.nodes.atoms.Type;

public class IllegalSectionTypeException extends LegislativeException{

  public IllegalSectionTypeException(int secNum, 
                                     Type declaredType, 
                                     int line, 
                                     int column, 
                                     String billName) {
    super("Section "+secNum+" has been set to produce a "+declaredType.getActualValue()+
          " but has no procedures to do so.", line, column, billName);
  }

  public IllegalSectionTypeException(int secNum, 
                                     Type declaredType, 
                                     Type actualType, 
                                     int line, 
                                     int column, 
                                     String billName) {
    super("Section "+secNum+" has been set to produce a "+declaredType.getActualValue()+
          " but has its procedures produce a "+actualType+" instead.", line, column, billName);
  }
}
