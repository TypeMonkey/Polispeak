package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;

public class UnfoundLegislationException extends LegislativeException{

  public UnfoundLegislationException(String unfoundLegislation, int line, int column, String billName) {
    super("There's no legislation called '"+unfoundLegislation+"'", line, column, billName);
  }

}
