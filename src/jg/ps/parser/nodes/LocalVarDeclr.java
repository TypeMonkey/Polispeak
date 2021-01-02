package jg.ps.parser.nodes;

import java.util.Arrays;

import jg.ps.parser.nodes.atoms.Type;

/**
 * Describes a local holder (variable) that can only hold a certain type of
 * objects, as well as an initial hold.
 * 
 * @author Jose
 */
public class LocalVarDeclr extends Expr{

  private final String name;
  private final Type type;
  private final Expr value;
  private final Expr [] body;
  
  public LocalVarDeclr(int line, int column, String name, Type varType, Expr value, Expr [] body) {
    super(line, column);
    this.name = name;
    this.type = varType;
    this.value = value;
    this.body = body;
  }
  
  public String getName() {
    return name;
  }
  
  public Expr getValue() {
    return value;
  }
  
  public Expr [] getBody() {
    return body;
  }
  
  public Type getType() {
    return type;
  }
  
  @Override
  public String toString() {
    return "LVAR ~ '"+name+"':"+type+" = "+value+" , body: "+Arrays.toString(body);
  }
}
