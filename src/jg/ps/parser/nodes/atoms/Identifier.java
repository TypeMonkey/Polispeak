package jg.ps.parser.nodes.atoms;

public class Identifier extends Atom<String>{

  public Identifier(String identifier, int line, int column) {
    super(identifier, line, column);
  }

  @Override
  public String toString() {
    return "IDEN ~ "+actualValue;
  }
  
  public String getIdentifier() {
    return actualValue;
  }
}
