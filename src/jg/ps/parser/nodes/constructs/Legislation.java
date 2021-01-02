package jg.ps.parser.nodes.constructs;

import java.util.Map;

import jg.ps.parser.nodes.Expr;

public class Legislation extends Expr{

  private final int billNumber;
  private final String name;
  private final String description;
    
  private final Section [] sections; 
  private final Map<String, Definition> definitons;
  
  public Legislation(int billNumber, 
              String name, 
              String description, 
              Section [] sections, 
              Map<String, Definition> definitons) {
    super(-1, -1);  
    this.billNumber = billNumber;
    this.name = name;
    this.description = description;
    this.sections = sections;
    this.definitons = definitons;
  }

  public int getBillNumber() {
    return billNumber;
  }
  
  public String getName() {
    return name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public Section[] getSections() {
    return sections;
  }
  
  public Map<String, Definition> getDefinitons() {
    return definitons;
  }
  
  @Override
  public String toString() {
    String bill = "RESOLUTION "+billNumber+", TITLED: '"+name+"'" + System.lineSeparator();
    
    bill += " DESCRIPTION: "+description + System.lineSeparator();
    
    bill += " * Definitions: " + System.lineSeparator();
    
    for(Definition definition : definitons.values()) {
      bill += definition + System.lineSeparator();
    }
    
    bill += " * Sections: " + System.lineSeparator();
    
    for(Section section : sections) {
      bill += section + System.lineSeparator();
    }
    
    
    return bill;
  }
}
