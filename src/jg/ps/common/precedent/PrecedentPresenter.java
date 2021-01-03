package jg.ps.common.precedent;

import java.lang.reflect.Method;
import java.util.Map;

import jg.ps.parser.nodes.constructs.Legislation;

public class PrecedentPresenter {

  private Legislation rep;
  private Method [] methods;
  private Map<String, Class<?>> defs;
  
  public PrecedentPresenter(Legislation rep, Method [] methods, Map<String, Class<?>> defs) {
    this.rep = rep;
    this.methods = methods;
    this.defs = defs;
  }
  
  public Legislation getRep() {
    return rep;
  }
  
  public Method[] getMethods() {
    return methods;
  }
  
  public Map<String, Class<?>> getDefs() {
    return defs;
  }
  
}
