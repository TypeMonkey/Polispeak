package jg.ps.enforcement.instances.primitives;

import jg.ps.parser.nodes.atoms.Type;

public class FloatInstance extends Primitive<Double>{

  public FloatInstance(Double value) {
    super(Type.FLOAT_TYPE.getActualValue(), value);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FloatInstance) {
      return ((FloatInstance) obj).getValue().equals(getValue());
    }
    return false;
  }
}
