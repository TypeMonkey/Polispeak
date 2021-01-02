package jg.ps.parser.nodes.atoms;

import jg.ps.parser.nodes.Expr;

public abstract class Atom<T> extends Expr{

  protected final T actualValue;
  
  public Atom(T value, int line, int column) {
    super(line, column);
    this.actualValue = value;
  }
  
  public T getActualValue() {
    return actualValue;
  }
  
  @Override
  public abstract String toString();
}

