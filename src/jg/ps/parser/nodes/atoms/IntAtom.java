package jg.ps.parser.nodes.atoms;

/**
 * Describes a non-decimal whole number in Polispeak.
 * 
 * In software development, such number is an Integer.
 * Internally, Polispeak Integers are 64-bits.
 * 
 * @author Jose
 */
public class IntAtom extends Atom<Long>{

  /**
   * Constructs an IntAtom
   * @param value - the actual integer represented by this whole number
   * @param line - the line number where this number was found
   * @param column - the column number where this number was found
   */
  public IntAtom(Long value, int line, int column) {
    super(value, line, column);
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "INT ~ "+actualValue;
  }
}
