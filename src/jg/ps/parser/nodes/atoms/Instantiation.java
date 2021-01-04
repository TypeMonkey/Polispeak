package jg.ps.parser.nodes.atoms;

import java.util.Arrays;

import jg.ps.parser.nodes.Expr;

/**
 * Describes the instantiation of a definition.
 * @author Jose
 */
public class Instantiation extends Atom<Type>{

  private final Expr [] provisions;
  
  public Instantiation(int line, int column, Type value, Expr ... provisions) {
    super(value, line, column);
    this.provisions = provisions;
  }
  
  public Expr[] getProvisions() {
    return provisions;
  }
  
  public int getProvisionCount() {
    return provisions.length;
  }
  
  public Type getType() {
    return actualValue;
  }

  @Override
  public String toString() {
    return "INSTANCE ~ "+actualValue+" , provs: "+Arrays.toString(provisions);
  }
}
