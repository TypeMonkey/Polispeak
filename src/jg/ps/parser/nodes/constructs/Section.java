package jg.ps.parser.nodes.constructs;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.Type;

public class Section extends Expr{

  private final int sectionNumber;
  private final String sectionTitle;
  private final Type resultType;
  //private final String sectionDesc;
  
  private final LinkedHashMap<String, Type> provisions;
  private final Expr [] body;
  
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
  
  /*
  public boolean hasDescription() {
    return sectionDesc != null;
  }
  */
  
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
