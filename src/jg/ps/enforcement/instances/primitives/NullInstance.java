package jg.ps.enforcement.instances.primitives;

import jg.ps.parser.nodes.atoms.Type;

public class NullInstance extends Primitive<Void>{

  private static final NullInstance NULL = new NullInstance();
  
  private NullInstance() {
    super(Type.NULL_TYPE.getActualValue(), null);
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof NullInstance) {
      return true;
    }
    return false;
  }
  
  public static NullInstance getNullInstance() {
    return NULL;
  }
}
