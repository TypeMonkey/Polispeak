package jg.ps.cohesion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jg.ps.cohesion.exception.UnfoundDefinitionException;
import jg.ps.cohesion.exception.UnfoundLegislationException;
import jg.ps.cohesion.exception.UnfoundSectionException;
import jg.ps.common.LegislativeException;
import jg.ps.parser.errors.UnfoundHoldException;
import jg.ps.parser.nodes.Conditional;
import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.InstanceDeref;
import jg.ps.parser.nodes.Invocation;
import jg.ps.parser.nodes.LocalVarDeclr;
import jg.ps.parser.nodes.atoms.Identifier;
import jg.ps.parser.nodes.atoms.Instantiation;
import jg.ps.parser.nodes.atoms.Type;
import jg.ps.parser.nodes.constructs.Legislation;
import jg.ps.parser.nodes.constructs.Definition;
import jg.ps.parser.nodes.constructs.Section;

public class Validator {

  private final Map<String, Legislation> allBills;
  
  public Validator(Map<String, Legislation> allBills) {
    this.allBills = allBills;
  }
  
  public List<LegislativeException> validate(Legislation bill){
    ArrayList<LegislativeException> exceptions = new ArrayList<>();
    
    //Validate sections
    for(Section section : bill.getSections()) {
      exceptions.addAll(validateSection(section, bill.getName()));
    }
    
    //validate definitions
    for(Definition definition : bill.getDefinitons().values()) {
      exceptions.addAll(validateDefinition(definition, bill.getName()));
    }
    
    return exceptions;
  }
  
  private List<LegislativeException> validateSection(Section section, String curBill){
    ArrayList<LegislativeException> exceptions = new ArrayList<>();

    for(Type provType : section.getProvisions().values()) {
      exceptions.addAll(validateType(provType, curBill));
    }
    
    //validate section body
    for(Expr expr : section.getBody()) {
      exceptions.addAll(validateExpr(expr, Arrays.asList(section.getProvisions().keySet()), curBill));
    }
    
    return exceptions;
  }
  
  private List<LegislativeException> validateDefinition(Definition definition, String curBill){
    ArrayList<LegislativeException> exceptions = new ArrayList<>();

    for(Type memberType : definition.getMembers().values()) {
      exceptions.addAll(validateType(memberType, curBill));
    }
    
    return exceptions;
  } 
  
  private List<LegislativeException> validateType(Type type, String curBill) {
    ArrayList<LegislativeException> exceptions = new ArrayList<>();
    Legislation hostBill = allBills.get(type.getHostBill());
    if (hostBill != null) {
      if (!hostBill.getDefinitons().containsKey(type.getTypeName())) {
        exceptions.add(new UnfoundDefinitionException(type.getTypeName(), 
                                                      hostBill.getName(), 
                                                      type.getLineNumber(), 
                                                      type.getColumnNumber(), 
                                                      curBill));
      }
    }
    else {
      exceptions.add(new UnfoundLegislationException(type.getHostBill(), 
                                                     type.getLineNumber(), 
                                                     type.getColumnNumber(), 
                                                     curBill));
    }
    return exceptions;
  }
  
  private List<LegislativeException> validateExpr(Expr expr, List<Set<String>> vars, String curBill){
    ArrayList<LegislativeException> exceptions = new ArrayList<>();

    if (expr instanceof Identifier) {
      Identifier identifier = (Identifier) expr;
      if (!find(vars, identifier.getIdentifier())) {
        exceptions.add(new UnfoundHoldException(identifier.getIdentifier(), 
                                                identifier.getLineNumber(), 
                                                identifier.getColumnNumber(), 
                                                curBill));
      }
    }
    else if (expr instanceof Instantiation) {
      Instantiation instanciation = (Instantiation) expr;
      exceptions.addAll(validateType(instanciation.getType(), curBill));
      
      for(Expr prov : instanciation.getProvisions()) {
        exceptions.addAll(validateExpr(prov, vars, curBill));
      }
    }
    else if (expr instanceof Conditional) {
      Conditional conditional = (Conditional) expr;
      
      exceptions.addAll(validateExpr(conditional.getClause(), vars, curBill));
      exceptions.addAll(validateExpr(conditional.getTrueRoute(), vars, curBill));
      exceptions.addAll(validateExpr(conditional.getFalseRoute(), vars, curBill));    
    }
    else if (expr instanceof InstanceDeref) {
      InstanceDeref deref = (InstanceDeref) expr;
      
      exceptions.addAll(validateExpr(deref.getTarget(), vars, curBill));
    }
    else if (expr instanceof Invocation) {
      Invocation invocation = (Invocation) expr;
      
      //check if section exists
      Legislation targetHost = allBills.get(invocation.getHostBill());
      if (targetHost != null) {
        int totalSections = targetHost.getSections().length;
        if (invocation.getSectionNumber() < 1 || invocation.getSectionNumber() > totalSections) {
          //not a valid section
          exceptions.add(new UnfoundSectionException(invocation.getSectionNumber(), 
                                                     targetHost.getName(), 
                                                     invocation.getLineNumber(), 
                                                     invocation.getColumnNumber(), 
                                                     curBill));
        }
      }
      else {
        exceptions.add(new UnfoundLegislationException(invocation.getHostBill(),
                                                       invocation.getLineNumber(), 
                                                       invocation.getColumnNumber(), 
                                                       curBill));
      }
      
      for (Expr prov : invocation.getProvisions()) {
        exceptions.addAll(validateExpr(prov, vars, curBill));
      }
    }
    else if (expr instanceof LocalVarDeclr) {
      LocalVarDeclr localVarDeclr = (LocalVarDeclr) expr;
      
      exceptions.addAll(validateExpr(localVarDeclr.getValue(), vars, curBill));
      
      HashSet<String> singleton = new HashSet<>();
      singleton.add(localVarDeclr.getName());
      
      List<Set<String>> newVars = concat(vars, singleton);
      
      for(Expr bodySegment : localVarDeclr.getBody()) {
        exceptions.addAll(validateExpr(bodySegment, newVars, curBill));
      }
    }
    
    return exceptions;
  }
  
  private List<Set<String>> concat(List<Set<String>> list, Set<String> newNames){
    ArrayList<Set<String>> newList = new ArrayList<>();
    newList.add(newNames);
    newList.addAll(list);
    return Collections.unmodifiableList(newList);
  }
  
  private boolean find(List<Set<String>> list, String name) {
    for (Set<String> set : list) {
      if (set.contains(name)) {
        return true;
      }
    }
    
    return false;
  }
}
