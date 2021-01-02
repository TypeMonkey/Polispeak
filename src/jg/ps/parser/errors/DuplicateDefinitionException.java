package jg.ps.parser.errors;

import jg.ps.common.LegislativeException;

public class DuplicateDefinitionException extends LegislativeException {

  public DuplicateDefinitionException(String defTitle, int line, int column, String billName) {
    super("'"+defTitle+"' is already defined in this resolution.", line, column, billName);
  }

}
