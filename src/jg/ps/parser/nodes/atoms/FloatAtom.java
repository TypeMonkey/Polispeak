package jg.ps.parser.nodes.atoms;

/**
 * Describes a decimal number in Polispeak.
 * 
 * In software development, such number is what's known as a floating-point number (a.k.a "floats").
 * Internally, Polispeak Floats are double-precision, 64-bit floating-point numbers.
 * 
 * @author Jose
 */
public class FloatAtom extends Atom<Double> {

  /**
   * Constructs a FloatAtom
   * @param value - the actual float represented by this number
   * @param line - the line number where this number was found
   * @param column - the column number where this number was found
   */
  public FloatAtom(Double value, int line, int column) {
    super(value, line, column);
  }

  @Override
  public String toString() {
    return "FLOAT ~ "+actualValue;
  }
}
