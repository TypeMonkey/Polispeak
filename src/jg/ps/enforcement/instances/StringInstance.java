package jg.ps.enforcement.instances;

import jg.ps.enforcement.instances.primitives.IntValue;
import jg.ps.parser.nodes.atoms.Type;

public class StringInstance extends DefInstance{

  private final String value;
  
  public StringInstance(String value) {
    super(Type.STR_TYPE.getActualValue());
    setMember("length", new IntValue(Long.valueOf(value.length())));
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
