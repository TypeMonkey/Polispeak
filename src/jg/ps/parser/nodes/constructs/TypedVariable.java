package jg.ps.parser.nodes.constructs;

import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.Type;

/**
 * A coupling for a variable, pairing the name of the variable with its type.
 * @author Jose
 */
public class TypedVariable extends Expr{

  private final Type type;
  private final String varName;
  
  /**
   * Constructs a TypedVariable
   * @param line - the line number where this variable is
   * @param column - the column number where this variable is
   * @param type - the Type of this variable
   * @param varName - the name of this variable
   */
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
