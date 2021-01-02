package jg.ps.parser.nodes;

import java.util.Arrays;

/**
 * Describes the invocation of a Section (invocation of a function) in a resolution.
 * 
 * A Section Invocation is described with the number of the section being invoked,
 * the bill that houses/host said section, as well
 * as any necessary provisions needed to properly invoke said section.
 * 
 * @author Jose
 */
public class Invocation extends Expr{

  private final int sectionNumber;
  private final String hostBill;
  private final Expr [] provisions;
  
  public Invocation(int line, int column, int sectionNumber, String hostBill, Expr ... provisions) {
    super(line, column);
    this.sectionNumber = sectionNumber;
    this.hostBill = hostBill;
    this.provisions = provisions;
  }
  
  public Expr[] getProvisions() {
    return provisions;
  }
  
  public String getHostBill() {
    return hostBill;
  }
  
  public int getSectionNumber() {
    return sectionNumber;
  }
  
  @Override
  public String toString() {
    return "INVOC ~ "+hostBill+"$SEC-"+sectionNumber+" "+Arrays.toString(provisions);
  }

}
