package jg.ps.enforcement.instances.primitives;

import jg.ps.parser.nodes.atoms.Type;

public class Null extends Primitive<Void>{

  private static final Null NULL = new Null();
  
  private Null() {
    super(Type.NULL_TYPE.getActualValue(), null);
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof Null) {
      return true;
    }
    return false;
  }
  
  public static Null getNullInstance() {
    return NULL;
  }
}
