package jg.ps.parser.nodes.constructs;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.Type;

/**
 * A section is the most basic body of procedure 
 * in a legislation, at least in Polispeak.
 * 
 * Sections are uniquely identified by their section number - which
 * is assigned with respect to other sections in their host legislation,
 * with the top most section being assigned 1.
 * 
 * In software development, section = function and so, the provisions
 * to a section are it's parameters. 
 * 
 * @author Jose
 */
public class Section extends Expr{

  private final int sectionNumber;
  private final String sectionTitle;
  private final Type resultType;
  
  private final LinkedHashMap<String, Type> provisions;
  private final Expr [] body;
  
  /**
   * Constructs a Section
   * @param line - the starting line number of this Section
   * @param column - the starting column number of this Section
   * @param sectionNumber - the positive, non-zero integer that uniquely identifies this Section
   * @param sectionTitle - the title of this Section that briefly describes its purpose
   * @param provisions - A mapping that describes the amount, order and type of this Section's provisions
   * @param resultType - the resulting type of enforcing this Section
   * @param body - the procedures that makeup this Section
   */
  public Section(int line, 
                 int column, 
                 int sectionNumber, 
                 String sectionTitle, 
                 LinkedHashMap<String, Type> provisions, 
                 Type resultType,
                 Expr [] body) {
    super(line, column);
    this.sectionNumber = sectionNumber;
    this.sectionTitle = sectionTitle;
    this.resultType = resultType;
    
    this.provisions = provisions;
    this.body = body;
  }
  
  public boolean isTitled() {
    return sectionTitle != null;
  }
  
  public boolean isNotVoid() {
    return !resultType.equals(Type.VOID_TYPE);
  }
  
  public Type getResultType() {
    return resultType;
  }
  
  public LinkedHashMap<String, Type> getProvisions() {
    return provisions;
  }
  
  public Expr[] getBody() {
    return body;
  }
  
  public int getSectionNumber() {
    return sectionNumber;
  }
  
  public String getSectionTitle() {
    return sectionTitle;
  }
  
  public int getProvisionCount() {
    return provisions.size();
  }
  
  /*
  public String getSectionDescription() {
    return sectionDesc;
  }
  */
  
  @Override
  public String toString() {
    String section = " ! Section "+sectionNumber+": "+ (isTitled() ? sectionTitle : "") + System.lineSeparator();
    
    /*
    if (hasDescription()) {
      section += "   Description: "+sectionDesc + System.lineSeparator();
    }
    */

    if (isNotVoid()) {
      section += "   Results in a "+resultType + System.lineSeparator();
    }
    
    for(Entry<String, Type> prov : provisions.entrySet()) {
      section += "  **PROV: "+prov.getKey()+" <-> "+prov.getValue() + System.lineSeparator();
    }
    
    for(Expr step : body) {
      section += "   -> "+ step + System.lineSeparator();
    }
    
    return section;
  }
}
