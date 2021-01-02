package jg.ps.parser.nodes.atoms;

public class Str extends Atom<String>{

  public Str(String value, int line, int column) {
    super(value, line, column);
  }

  @Override
  public String toString() {
    return "STR ~ "+actualValue;
  }
  
  public String getString() {
    return actualValue;
  }
}
