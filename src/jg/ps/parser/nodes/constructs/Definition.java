package jg.ps.parser.nodes.constructs;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.Type;

public class Definition extends Expr{

  private final String name;
  private final LinkedHashMap<String, Type> members;
  
  public Definition(int line, int column, String name, LinkedHashMap<String, Type> members) {
    super(line, column);
    this.name = name;
    this.members = members;
  }

  public String getName() {
    return name;
  }
  
  public LinkedHashMap<String, Type> getMembers() {
    return members;
  }
  
  @Override
  public String toString() {
    String def = " -> DEF for '"+name+"'" + System.lineSeparator();
    
    for(Entry<String, Type> member : members.entrySet()) {
      def += "   * "+member.getKey()+" <-> "+member.getValue() + System.lineSeparator();
    }
    
    return def;
  }
}
