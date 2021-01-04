package jg.ps.parser.nodes.atoms;

/**
 * Describes a string expressed in legislation.
 * 
 * Strings are a sequence of characters (alphabetic, numeric, symbolic, etc.)
 * within an enclosing set of double quotes.
 * 
 * @author Jose
 */
public class StrAtom extends Atom<String>{

  /**
   * Constructs a StrAtom
   * @param value - the actual sequence of this string, without the enclosing quotes
   * @param line - the line number where this string is located
   * @param column - the column number where this string is located
   */
  public StrAtom(String value, int line, int column) {
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
