package jg.ps.cohesion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jg.ps.cohesion.exception.ConditionalException;
import jg.ps.cohesion.exception.IllegalHoldException;
import jg.ps.cohesion.exception.IllegalSectionInvocation;
import jg.ps.cohesion.exception.IllegalSectionTypeException;
import jg.ps.cohesion.exception.IllegalTypeUseException;
import jg.ps.cohesion.exception.InstanciationException;
import jg.ps.cohesion.exception.UnfoundComponentException;
import jg.ps.parser.nodes.Conditional;
import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.InstanceDeref;
import jg.ps.parser.nodes.Invocation;
import jg.ps.parser.nodes.LocalVarDeclr;
import jg.ps.parser.nodes.atoms.FloatAtom;
import jg.ps.parser.nodes.atoms.Identifier;
import jg.ps.parser.nodes.atoms.Instantiation;
import jg.ps.parser.nodes.atoms.IntAtom;
import jg.ps.parser.nodes.atoms.NullValue;
import jg.ps.parser.nodes.atoms.StrAtom;
import jg.ps.parser.nodes.atoms.Type;
import jg.ps.parser.nodes.constructs.Legislation;
import jg.ps.parser.nodes.constructs.Definition;
import jg.ps.parser.nodes.constructs.Section;

public class TypeChecker {

  private final Map<String, Legislation> allBills;
  
  public TypeChecker(Map<String, Legislation> allBills) {
    this.allBills = allBills;
  }
  
  public void check(Legislation bill){    
    //Validate sections
    for(Section section : bill.getSections()) {
      checkSection(section, bill);
    }     
    
    //validate sections
    for(Definition def : bill.getDefinitons().values()) {
      checkDefinition(def, bill);
    }
  }
  
  private void checkDefinition(Definition def, Legislation curBill) {
    for(Type memberType : def.getMembers().values()) {
      if (memberType.equals(Type.VOID_TYPE) || 
          memberType.equals(Type.NULL_TYPE)) {
        throw new IllegalTypeUseException(memberType.getActualValue(), 
                                          def.getLineNumber(), 
                                          def.getColumnNumber(), 
                                          curBill.getName());
      }
    }
  }
  
  private void checkSection(Section section, Legislation curBill){    
    Type recentType = null;
    
    //validate section body
    for(Expr expr : section.getBody()) {
      recentType = checkExpr(expr, wrap(section.getProvisions()), curBill);
    }
    
    if (recentType == null) {
      if (section.isNotVoid()) {
        //throw error
        throw new IllegalSectionTypeException(section.getSectionNumber(), 
                                              section.getResultType(), 
                                              section.getLineNumber(), 
                                              section.getColumnNumber(), 
                                              curBill.getName());
      }
    }
    else{
      if (section.isNotVoid() && !section.getResultType().equals(recentType)) {
        //throw error
        throw new IllegalSectionTypeException(section.getSectionNumber(), 
                                              section.getResultType(), 
                                              recentType,
                                              section.getLineNumber(), 
                                              section.getColumnNumber(), 
                                              curBill.getName());
      }
    }  
  }
  
  private Type checkExpr(Expr expr, List<Map<String, Type>> vars, Legislation curBill){
    if (expr instanceof Identifier) {
      Identifier identifier = (Identifier) expr;
      return find(vars, identifier.getIdentifier());
    }
    else if (expr instanceof FloatAtom) {
      return Type.FLOAT_TYPE;
    }
    else if (expr instanceof IntAtom) {
      return Type.INT_TYPE;
    }
    else if (expr instanceof StrAtom) {
      return Type.STR_TYPE;
    }
    else if (expr instanceof NullValue) {
      return Type.NULL_TYPE;
    }
    else if (expr instanceof Instantiation) {
      Instantiation instanciation = (Instantiation) expr;
      
      if (instanciation.getType().equals(Type.VOID_TYPE) || 
          instanciation.getType().equals(Type.NULL_TYPE)) {
        throw new IllegalTypeUseException(instanciation.getType().getActualValue(), 
                                          instanciation.getLineNumber(), 
                                          instanciation.getColumnNumber(), 
                                          curBill.getName());
      }
      
      Legislation hostBill = allBills.get(instanciation.getType().getHostBill());
      Definition target = hostBill.getDefinitons().get(instanciation.getType().getTypeName());
      
      Type [] provTypes = new Type[instanciation.getProvisions().length];
      for (int i = 0; i < provTypes.length; i++) {
        provTypes[i] = checkExpr(instanciation.getProvisions()[i], vars, curBill);
      }
      
      Type [] defTypes = target.getMembers().values().toArray(new Type[target.getMembers().size()]);
      
      if (provTypes.length != defTypes.length) {
        //throw error
        throw new InstanciationException(target.getName(), 
                                         hostBill.getName(), 
                                         provTypes.length, 
                                         defTypes.length, 
                                         instanciation.getLineNumber(), 
                                         instanciation.getColumnNumber(), 
                                         curBill.getName());
      }
      
      for(int i = 0; i < defTypes.length; i++) {
        if (
            ( provTypes[i] != null ) &&
            ( defTypes[i].equals(Type.UNKNOWN_TYPE) || 
              isNullable(defTypes[i]) && provTypes[i].equals(Type.NULL_TYPE) || 
              defTypes[i].equals(provTypes[i])) ) {
          continue;
        }
        else {
          //throw error
          throw new InstanciationException(target.getName(), 
                                           hostBill.getName(), 
                                           i+1, 
                                           defTypes[i], 
                                           provTypes[i], 
                                           instanciation.getLineNumber(), 
                                           instanciation.getColumnNumber(), 
                                           curBill.getName());
        }
      }
      
      return instanciation.getType();
    }
    else if (expr instanceof Conditional) {
      Conditional conditional = (Conditional) expr;
      
      //Clause must evaluate to an integer (Looking at you C)
      Type clauseType = checkExpr(conditional.getClause(), vars, curBill);
      
      if (!clauseType.equals(Type.INT_TYPE)) {
        //throw error
        throw new ConditionalException(clauseType, 
                                       conditional.getLineNumber(), 
                                       conditional.getColumnNumber(), 
                                       curBill.getName());
      }

      Type trueRoute = checkExpr(conditional.getTrueRoute(), vars, curBill);
      Type falseRoute = checkExpr(conditional.getFalseRoute(), vars, curBill);
      if (!trueRoute.equals(falseRoute)) {
        //throw error
        throw new ConditionalException(trueRoute, 
                                       falseRoute, 
                                       conditional.getLineNumber(), 
                                       conditional.getColumnNumber(), 
                                       curBill.getName());
      }
      
      return trueRoute;
    }
    else if (expr instanceof InstanceDeref) {
      InstanceDeref deref = (InstanceDeref) expr;
      
      Type targetType = checkExpr(deref.getTarget(), vars, curBill);
      
      Legislation hostBill = allBills.get(targetType.getHostBill());
      Definition target = hostBill.getDefinitons().get(targetType.getTypeName());
      
      Type memberType = target.getMembers().get(deref.getMemberName());
      if (memberType == null) {
        //throw error
        throw new UnfoundComponentException(targetType, 
                                            deref.getMemberName(), 
                                            deref.getLineNumber(), 
                                            deref.getColumnNumber(), 
                                            curBill.getName());
      }
      
      return memberType;
    }
    else if (expr instanceof Invocation) {
      Invocation invocation = (Invocation) expr;
      
      Type [] provTypes = new Type[invocation.getProvisions().length];
      for (int i = 0; i < provTypes.length; i++) {
        provTypes[i] = checkExpr(invocation.getProvisions()[i], vars, curBill);
      }
      
      Legislation hostBill = allBills.get(invocation.getHostBill());
      Section target = hostBill.getSections()[invocation.getSectionNumber() - 1];
      
      Type [] secTypes = target.getProvisions().values().toArray(new Type[target.getProvisions().size()]);

      if (provTypes.length != secTypes.length) {
        //throw error
        throw new IllegalSectionInvocation(target.getSectionNumber(), 
                                           hostBill.getName(), 
                                           provTypes.length, 
                                           secTypes.length, 
                                           invocation.getLineNumber(), 
                                           invocation.getColumnNumber(), 
                                           curBill.getName());
      }
      
      for(int i = 0; i < secTypes.length; i++) {
        if (secTypes[i].equals(Type.UNKNOWN_TYPE) || 
            isNullable(secTypes[i]) && provTypes[i].equals(Type.NULL_TYPE) || 
            secTypes[i].equals(provTypes[i])) {
          continue;
        }
        else {
          //throw error
          throw new IllegalSectionInvocation(target.getSectionNumber(), 
                                             hostBill.getName(), 
                                             i+1, 
                                             secTypes[i], 
                                             provTypes[i], 
                                             invocation.getLineNumber(), 
                                             invocation.getColumnNumber(), 
                                             curBill.getName());
        }
      }
      
      //void Sections return "null" to represent void
      //otherwise, return the target sections declared type
      return target.getResultType();
    }
    else if (expr instanceof LocalVarDeclr) {
      LocalVarDeclr localVar = (LocalVarDeclr) expr;
      
      if (localVar.getType().equals(Type.VOID_TYPE) || 
          localVar.getType().equals(Type.NULL_TYPE)) {
        throw new IllegalTypeUseException(localVar.getType().getActualValue(), 
                                          localVar.getLineNumber(), 
                                          localVar.getColumnNumber(), 
                                          curBill.getName());
      }
      
      Type initialValueType = checkExpr(localVar.getValue(), vars, curBill);
      
      if ( localVar.getType().equals(Type.UNKNOWN_TYPE) || 
           (isNullable(localVar.getType()) && initialValueType.equals(Type.NULL_TYPE)) || 
           localVar.getType().equals(initialValueType)) {
        
        HashMap<String, Type> singleton = new HashMap<>();
        singleton.put(localVar.getName(), localVar.getType());
        
        Type latestType = null;
        for(Expr segment : localVar.getBody()) {
          latestType = checkExpr(segment, concat(vars, singleton), curBill);
        }
        
        return latestType;
      }
      else {
        //throw error
        throw new IllegalHoldException(localVar.getName(), 
                                       localVar.getType(), 
                                       initialValueType, 
                                       localVar.getLineNumber(), 
                                       localVar.getColumnNumber(), 
                                       curBill.getName());
      }
    }
    
    throw new RuntimeException("FATAL ERROR: Unknown expr type "+expr.getClass());
  }
    
  private static List<Map<String, Type>> wrap(Map<String, Type> names){
    ArrayList<Map<String, Type>> namesList = new ArrayList<>();
    namesList.add(names);
    return Collections.unmodifiableList(namesList);
  }
  
  private static List<Map<String, Type>> concat(List<Map<String, Type>> list, Map<String, Type> newNames){
    ArrayList<Map<String, Type>> newList = new ArrayList<>();
    newList.add(newNames);
    newList.addAll(list);
    return Collections.unmodifiableList(newList);
  }
  
  private static Type find(List<Map<String, Type>> list, String name) {
    for (Map<String, Type> map : list) {
      if (map.containsKey(name)) {
        return map.get(name);
      }
    }
    
    return null;
  }

  private static boolean isNullable(Type type) {
    if (type.equals(Type.INT_TYPE) || type.equals(Type.FLOAT_TYPE)) {
      return false;
    }
    return true;
  }
}
