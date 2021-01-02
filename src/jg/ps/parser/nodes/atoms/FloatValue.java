package jg.ps.parser.nodes.atoms;

public class FloatValue extends Atom<Double> {

  public FloatValue(Double value, int line, int column) {
    super(value, line, column);
  }

  @Override
  public String toString() {
    return "FLOAT ~ "+actualValue;
  }
}
