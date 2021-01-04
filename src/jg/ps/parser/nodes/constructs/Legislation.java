package jg.ps.parser.nodes.constructs;

import java.util.Map;

import jg.ps.parser.nodes.Expr;

/**
 * Represents legislation that is currently being drafted or being enforced.
 * 
 * In software development speak, Legislation = Source Code.
 *  It's composed of Sections (= Functions), and Definitions (= Type Definitions)
 * 
 * Legislation is uniquely identified by its name. The number included with legislation
 * can be arbitrary and is largely ignored by the Polispeak Interpreter.
 * @author Jose
 */
public class Legislation extends Expr{

  private final int billNumber;
  private final String name;
  private final String description;
    
  private final Section [] sections; 
  private final Map<String, Definition> definitons;
  
  /**
   * Constructs a Legislation
   * @param billNumber - an arbitrary integer to mark this legislation
   * @param name - the name that uniquely identifies this legislation
   * @param description - a detailed description of this legislation
   * @param sections - the Sections that compose this legislation
   * @param definitons - the Definitions defined in this legislation
   */
  public Legislation(int billNumber, 
              String name, 
              String description, 
              Section [] sections, 
              Map<String, Definition> definitons) {
    super(0, 0);  
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
  
  public boolean hasSection(int sectionNumber) {
    return sectionNumber >= 1 && sectionNumber <= sections.length;
  }
  
  public boolean hasDefinition(String defName) {
    return definitons.containsKey(defName);
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
