package jg.ps.enforcement.instances;

import java.util.HashMap;
import java.util.Map;

public class DefInstance extends Instance{

  private final Map<String, Instance> member;
  
  public DefInstance(String type) {
    super(type);
    this.member = new HashMap<>();
  }

  public Instance getMember(String memberName) {
    return member.get(memberName);
  }
  
  public boolean hasMember(String memberName) {
    return member.containsKey(memberName);
  }
  
  public Instance setMember(String memberName, Instance newValue) {
    return member.put(memberName, newValue);
  }
}
