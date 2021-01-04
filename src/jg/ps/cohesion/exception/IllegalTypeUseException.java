package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;

public class IllegalTypeUseException extends LegislativeException{

  public IllegalTypeUseException(String illegalType, int line, int column, String billName) {
    super("The 'Void' and 'Null' types cannot be used as a provision, fulfillment or member type."
        + "These types also cannot be instansiated", line, column, billName);
  }

}
