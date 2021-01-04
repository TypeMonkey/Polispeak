package jg.ps.parser.errors;

import jg.ps.common.LegislativeException;

public class WrongLegislationNameException extends LegislativeException{

  public WrongLegislationNameException(String declaredName, int line, int column, String billName) {
    super("This given legislation is given the title '"+declaredName+"' , but its file is called '"+billName+"'", line, column, billName);
  }

}
