package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;

public class UnfoundDefinitionException extends LegislativeException{

  public UnfoundDefinitionException(String defName, String hostBill, int line, int column, String billName) {
    super("There's no definition of '"+defName+"' in the legislation '"+hostBill+"'", line, column, billName);
  }

}
