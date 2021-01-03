package jg.ps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jg.ps.enforcement.Executive;
import jg.ps.enforcement.VirtualSection;
import jg.ps.enforcement.instances.Instance;
import jg.ps.parser.Drafter;
import jg.ps.parser.nodes.constructs.Legislation;

public class Main {

  public static void main(String[] args) throws Exception {
    System.out.println("---POLISPEAK v1.0--- "+Arrays.toString("$what".split("\\$")));
        
    //System.out.println(TheConstitution.createTheConstitution().getConstitution());
    
    Drafter drafter = new Drafter("example.bill");
    Map<String, Legislation> draftedLegis = drafter.draftLegislation();
    
    TheConstitution theConstitution = TheConstitution.createTheConstitution();
    Map<String, List<VirtualSection>> virtualSections = new HashMap<>();
    virtualSections.put(theConstitution.getConstitution().getName(), theConstitution.getConstiVSections());
    virtualSections.put(theConstitution.getStringBill().getName(), theConstitution.getStringBillVSections());
    
    //System.out.println("---DRAFTED: "+draftedLegis);
    
    Executive executive = new Executive(draftedLegis, virtualSections);
    executive.enforceLegislation(draftedLegis.get("example"), new Instance[0]);
  }

}
