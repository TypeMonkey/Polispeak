package jg.ps.enforcement;

import jg.ps.enforcement.instances.Instance;

/**
 * Meant for sections described in the Constitution - and other founding legislation.
 * 
 * Overall, this is used for Sections that have steps that interact with facilities
 * that cannot be directly described in Polispeak
 * @author Jose
 *
 */
public abstract class VirtualSection {

  private final String hostBill;
  private final int sectionNumber;
  
  public VirtualSection(String hostBill, int sectionNumber) {
    this.hostBill = hostBill;
    this.sectionNumber = sectionNumber;
  }
  
  public abstract Instance fulfill(Instance ... provisions);
  
  public String getHostBill() {
    return hostBill;
  }
  
  public int getSectionNumber() {
    return sectionNumber;
  }
}
