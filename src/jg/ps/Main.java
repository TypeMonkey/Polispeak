package jg.ps;

import java.util.Arrays;
import java.util.Map;

import jg.ps.common.BillOfStrings;
import jg.ps.common.TheConstitution;
import jg.ps.common.precedent.PrecedentAnalyzer;
import jg.ps.common.precedent.PrecedentPresenter;
import jg.ps.enforcement.Executive;
import jg.ps.enforcement.instances.Instance;
import jg.ps.parser.Drafter;
import jg.ps.parser.nodes.constructs.Legislation;

public class Main {

  public static void main(String[] args) throws Exception {
    System.out.println("---POLISPEAK v1.0--- "+Arrays.toString("$what".split("\\$")));
      
    /*
    Map<String, PrecedentPresenter> precedents = PrecedentAnalyzer.analyzePrecedents(TheConstitution.class, BillOfStrings.class);
    for (PrecedentPresenter precs : precedents.values()) {
      System.out.println(precs.getRep());
    }
    */
    
    Map<String, PrecedentPresenter> precedents = PrecedentAnalyzer.analyzePrecedents(TheConstitution.class, 
                                                                                       BillOfStrings.class);
    Drafter drafter = new Drafter("example.bill");
    Map<String, Legislation> draftedLegis = drafter.draftLegislation(precedents);
    
    ///System.out.println("---DRAFTED: "+draftedLegis);
    
    Executive executive = new Executive(draftedLegis, precedents);
    executive.enforceLegislation(draftedLegis.get("example"), new Instance[0]);
  }

}
