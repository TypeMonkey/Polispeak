package jg.ps.enforcement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jg.ps.cohesion.exception.IllegalSectionInvocation;
import jg.ps.enforcement.instances.DefInstance;
import jg.ps.enforcement.instances.Instance;
import jg.ps.enforcement.instances.StringInstance;
import jg.ps.enforcement.instances.primitives.FloatInstance;
import jg.ps.enforcement.instances.primitives.IntValue;
import jg.ps.enforcement.instances.primitives.Null;
import jg.ps.parser.nodes.Conditional;
import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.InstanceDeref;
import jg.ps.parser.nodes.Invocation;
import jg.ps.parser.nodes.LocalVarDeclr;
import jg.ps.parser.nodes.atoms.FloatValue;
import jg.ps.parser.nodes.atoms.Identifier;
import jg.ps.parser.nodes.atoms.Instanciation;
import jg.ps.parser.nodes.atoms.Int;
import jg.ps.parser.nodes.atoms.NullValue;
import jg.ps.parser.nodes.atoms.Str;
import jg.ps.parser.nodes.atoms.Type;
import jg.ps.parser.nodes.constructs.Definition;
import jg.ps.parser.nodes.constructs.Legislation;
import jg.ps.parser.nodes.constructs.Section;

public class Executive {
  
  private final Map<String, Legislation> legislations;
  private final Map<String, List<VirtualSection>> virtualSections;
  
  public Executive(Map<String, Legislation> legislations, Map<String, List<VirtualSection>> virtualSections) {
    this.legislations = legislations;
    this.virtualSections = virtualSections;
  }
  
  public void enforceLegislation(Legislation legislation, Instance ... provisions) {
    /*
     * When enforcing Legislation, we execute its Section 1
     */
    
    Section firstSection = legislation.getSections()[0];
    if (firstSection.getProvisions().size() != provisions.length) {
      //make sure given provisions have the same type as what's declared
      //if not, throw an error
      throw new RuntimeException("Section 1 of "+legislation.getName()+
                                 " needs "+firstSection.getProvisions().size()+
                                 " but was only given "+provisions.length);
    }
    
    int i = 0;
    for(Type declaredType : firstSection.getProvisions().values()) {
      if (!declaredType.getActualValue().equals(provisions[i].getType())) {
        throw new RuntimeException("The "+IllegalSectionInvocation.correctIndex(i)+
                                   " provision of Section 1 of "+legislation.getName()+
                                   "must be a "+declaredType.getActualValue()+
                                   " but was provided a "+provisions[i].getType());
      }     
      i++;
    }
    
    Instance result = fulfillSection(firstSection, legislation.getName(), provisions);
  }
  
  private Instance fulfillSection(Section section, String currentBill, Instance ... provisions) {
    HashMap<String, Instance> localVarMap = new HashMap<>();
    
    //assign provisions to local map
    int i = 0;
    for(String provName : section.getProvisions().keySet()) {
      localVarMap.put(provName, provisions[i]);
      i++;
    }
    
    //Set result value to be the Null instance by default
    Instance lastResult = Null.getNullInstance();
    
    //iterate through section body and enforce each step
    for(Expr step : section.getBody()) {
      lastResult = fulfillStep(step, currentBill, wrap(localVarMap));
    }
    
    return lastResult;
  }
  
  private Instance fulfillStep(Expr step, String currentBill, List<Map<String, Instance>> vMap) {
    if (step instanceof FloatValue) {
      FloatValue value = (FloatValue) step;
      return new FloatInstance(value.getActualValue());
    }
    else if (step instanceof Identifier) {
      Identifier identifier = (Identifier) step;
      return find(vMap, identifier.getActualValue());
    }
    else if (step instanceof Int) {
      Int integer = (Int) step;
      return new IntValue(integer.getActualValue());
    }
    else if (step instanceof NullValue) {
      return Null.getNullInstance();
    }
    else if (step instanceof Str) {
      Str str = (Str) step;
      return new StringInstance(str.getActualValue());
    }
    else if (step instanceof Instanciation) {
      Instanciation instanciation = (Instanciation) step;
      
      Legislation hostLegislations = legislations.get(instanciation.getType().getHostBill());
      Definition definition = hostLegislations.getDefinitons().get(instanciation.getType().getTypeName());
      
      DefInstance instance = new DefInstance(instanciation.getType().getActualValue());
      
      int i = 0;
      for(String memberName : definition.getMembers().keySet()) {
        Instance provInstance = fulfillStep(instanciation.getProvisions()[i], currentBill, vMap);
        instance.setMember(memberName, provInstance);
        i++;
      }
      
      return instance;
    }
    else if (step instanceof Invocation) {
      Invocation invocation = (Invocation) step;
      
      Legislation hostBill = legislations.get(invocation.getHostBill());
      Section targetSection = hostBill.getSections()[invocation.getSectionNumber() - 1];
      
      Instance [] provs = new Instance[invocation.getProvisions().length];
      for (int i = 0; i < provs.length; i++) {
        provs[i] = fulfillStep(invocation.getProvisions()[i], currentBill, vMap);
      }
      
      System.out.println("---FULFILLING SECTION "+targetSection.getSectionNumber()+" of "+hostBill.getName());
      
      if (virtualSections.containsKey(hostBill.getName())) {
        return virtualSections.get(hostBill.getName()).get(targetSection.getSectionNumber() - 1).fulfill(provs);
      }
      else {
        return fulfillSection(targetSection, hostBill.getName(), provs);
      }
    }
    else if (step instanceof InstanceDeref) {
      InstanceDeref deref = (InstanceDeref) step;
      
      //this is guaranteed to not throw a cast exception assuming it passed type checking
      DefInstance target = (DefInstance) fulfillStep(deref.getTarget(), currentBill, vMap);
      
      return target.getMember(deref.getMemberName());
    }
    else if (step instanceof Conditional) {
      Conditional cond = (Conditional) step;
      
      // 0 = false
      // nonzero = true
      
      //this is guaranteed to not throw a cast exception assuming it passed type checking
      IntValue clauseResult = (IntValue) fulfillStep(cond.getClause(), currentBill, vMap);
      
      if (clauseResult.getValue() != 0) {
        return fulfillStep(cond.getTrueRoute(), currentBill, vMap);
      }     
      return fulfillStep(cond.getFalseRoute(), currentBill, vMap);
    }
    else if (step instanceof LocalVarDeclr) {
      LocalVarDeclr lvar = (LocalVarDeclr) step;
      
      HashMap<String, Instance> lvarMap = new HashMap<>();
      
      Instance initValue = fulfillStep(lvar.getValue(), currentBill, vMap);
      lvarMap.put(lvar.getName(), initValue);
      
      //create new map to use when enforcing the new context
      vMap = concat(vMap, lvarMap);
      
      Instance lastInstance = null;
      for(Expr bodySegment : lvar.getBody()) {
        lastInstance = fulfillStep(bodySegment, currentBill, vMap);
      }
      
      return lastInstance;
    }    
    throw new RuntimeException("============MAJOR ERROR====== "+step.getClass());
  } 
  
 
  
  
  private static List<Map<String, Instance>> wrap(Map<String, Instance> map){
    ArrayList<Map<String, Instance>> newList = new ArrayList<>();
    newList.add(map);
    return newList;
  }
  
  private static List<Map<String, Instance>> concat(List<Map<String, Instance>> list, Map<String, Instance> newMap){
    ArrayList<Map<String, Instance>> newList = new ArrayList<>();
    newList.add(newMap);
    newList.addAll(list);
    return Collections.unmodifiableList(newList);
  }
  
  private static Instance find(List<Map<String, Instance>> list, String name) {
    for (Map<String, Instance> map : list) {
      if (map.containsKey(name)) {
        return map.get(name);
      }
    }
    
    return null;
  }
}
