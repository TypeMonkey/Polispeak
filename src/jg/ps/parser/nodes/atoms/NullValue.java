package jg.ps.parser.nodes.atoms;

public class NullValue extends Atom<String>{

  public NullValue(int line, int column) {
    super("null", line, column);
  }

  @Override
  public String toString() {
    return "NULL_VAL";
  }

}
