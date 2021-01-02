package jg.ps.enforcement.instances.primitives;

import jg.ps.parser.nodes.atoms.Type;

public class IntValue extends Primitive<Long>{

  public IntValue(Long value) {
    super(Type.INT_TYPE.getActualValue(), value);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IntValue) {
      return ((IntValue) obj).getValue().equals(getValue());
    }
    return false;
  }
}
