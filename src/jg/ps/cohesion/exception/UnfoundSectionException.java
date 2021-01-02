package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;

public class UnfoundSectionException extends LegislativeException{

  public UnfoundSectionException(int sectionNum, String hostBill, int line, int column, String billName) {
    super("Section "+sectionNum+" doesn't exist in the legislation '"+hostBill+"'", line, column, billName);
  }
  
}
