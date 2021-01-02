package jg.ps.parser.nodes.constructs;

import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.Type;

public class TypedVariable extends Expr{

  private final Type type;
  private final String varName;
  
  public TypedVariable(int line, int column, Type type, String varName) {
    super(line, column);
    this.type = type;
    this.varName = varName;
  }
  
  public Type getType() {
    return type;
  }
  
  public String getVarName() {
    return varName;
  }

  @Override
  public String toString() {
    return "TYPED_VAR ~ "+type+" '"+varName+"'";
  }
}
