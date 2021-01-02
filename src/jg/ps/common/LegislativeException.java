package jg.ps.common;

/**
 * Parent of all exceptions - be it during drafting or enforcement - in Polispeak
 * @author Jose
 *
 */
public class LegislativeException extends RuntimeException{

  public LegislativeException(String message, int line, int column, String billName) {
    super(message+" in bill '"+billName+"' at ln "+line+", col "+column);
  }
  
}
