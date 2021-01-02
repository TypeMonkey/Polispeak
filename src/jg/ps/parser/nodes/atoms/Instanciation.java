package jg.ps.parser.nodes.atoms;

import java.util.Arrays;

import jg.ps.parser.nodes.Expr;

public class Instanciation extends Atom<Type>{

  private final Expr [] provisions;
  
  public Instanciation(int line, int column, Type value, Expr ... provisions) {
    super(value, line, column);
    this.provisions = provisions;
  }
  
  public Expr[] getProvisions() {
    return provisions;
  }
  
  public Type getType() {
    return actualValue;
  }

  @Override
  public String toString() {
    return "INSTANCE ~ "+actualValue+" , provs: "+Arrays.toString(provisions);
  }
}
