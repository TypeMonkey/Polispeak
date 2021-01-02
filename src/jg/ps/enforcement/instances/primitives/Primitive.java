package jg.ps.enforcement.instances.primitives;

import jg.ps.enforcement.instances.Instance;
import jg.ps.parser.nodes.atoms.Type;

/**
 * Represents an instance of a primitive type, like an Integer or Float.
 * @author Jose
 *
 * @param <T>
 */
public abstract class Primitive<T> extends Instance {

  private final T value;
  
  public Primitive(String type, T value) {
    super(type);
    this.value = value;
  }

  public T getValue() {
    return value;
  }
  
  public abstract boolean equals(Object o);
}
