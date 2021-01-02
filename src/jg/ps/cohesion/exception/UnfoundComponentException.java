package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;
import jg.ps.parser.nodes.atoms.Type;

public class UnfoundComponentException extends LegislativeException{

  public UnfoundComponentException(Type targetType, String componentName, int line, int column, String billName) {
    super("The definition '"+targetType+"' has no component named '"+componentName+"'", line, column, billName);
  }

}
