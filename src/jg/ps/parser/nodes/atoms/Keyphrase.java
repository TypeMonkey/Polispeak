package jg.ps.parser.nodes.atoms;

import net.percederberg.grammatica.parser.Token;

public class Keyphrase extends Atom<Token>{

  public Keyphrase(Token value, int line, int column) {
    super(value, line, column);
  }

  public String getPhrase() {
    return actualValue.getImage();
  }
  
  @Override
  public String toString() {
    return "KEY ~ "+actualValue.getImage();
  }

}
