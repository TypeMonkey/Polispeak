package jg.ps.parser.nodes.atoms;

public class Int extends Atom<Long>{

  public Int(Long value, int line, int column) {
    super(value, line, column);
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "INT ~ "+actualValue;
  }
}
