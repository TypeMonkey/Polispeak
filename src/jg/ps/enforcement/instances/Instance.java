package jg.ps.enforcement.instances;

import jg.ps.parser.nodes.atoms.Type;

public abstract class Instance {

  private final String type;
  
  public Instance(String type) {
    this.type = type;
  }
  
  public String getType() {
    return type;
  }
}
