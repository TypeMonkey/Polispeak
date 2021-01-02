package jg.ps.cohesion.exception;

import jg.ps.common.LegislativeException;
import jg.ps.parser.nodes.atoms.Type;

public class ConditionalException extends LegislativeException{

  public ConditionalException(Type givenClauseType, int line, int column, String billName) {
    super("A clause, when fulfilled, is expected to produce An Intger. "+
          "In this case, it produces a "+givenClauseType.getActualValue(), line, column, billName);
    // TODO Auto-generated constructor stub
  }

  public ConditionalException(Type trueRoute, Type falseRoute, int line, int column, String billName) {
    super("Both the True and False Routes of a clause must produce the same type of object."+System.lineSeparator()+
          " In this case, the True Route produces a "+trueRoute.getActualValue()+System.lineSeparator()+
          " while the False Route produces a "+falseRoute.getActualValue(), line, column, billName);
    // TODO Auto-generated constructor stub
  }
}
