package jg.ps.parser.errors;

import jg.ps.common.LegislativeException;

public class BadConclusionException extends LegislativeException{

  public BadConclusionException(int sectionNum, int line, int column, String billName) {
    super("Section "+sectionNum+" doesn't have a valid conclusion.", line, column, billName);
    // TODO Auto-generated constructor stub
  }
  
}
