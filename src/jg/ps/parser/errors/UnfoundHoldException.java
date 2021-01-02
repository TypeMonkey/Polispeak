package jg.ps.parser.errors;

import jg.ps.common.LegislativeException;

public class UnfoundHoldException extends LegislativeException{

  public UnfoundHoldException(String holdName, int line, int column, String billName) {
    super("There's no hold designation named '"+holdName+"'", line, column, billName);
  }
  
}
