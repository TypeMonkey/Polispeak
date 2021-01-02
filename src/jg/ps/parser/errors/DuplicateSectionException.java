package jg.ps.parser.errors;

import jg.ps.common.LegislativeException;

public class DuplicateSectionException extends LegislativeException{

  public DuplicateSectionException(int culpritSectionNumber, int line, int column, String billName) {
    super("There's already a Section that's numbered '"+culpritSectionNumber+"'", line, column, billName);
  }

  public DuplicateSectionException(String culpritSectionName, int line, int column, String billName) {
    super("There's already a Section that's titled '"+culpritSectionName+"'", line, column, billName);
  }
}
