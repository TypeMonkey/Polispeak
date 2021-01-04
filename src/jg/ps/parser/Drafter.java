package jg.ps.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jg.ps.cohesion.TypeChecker;
import jg.ps.cohesion.Validator;
import jg.ps.common.LegislativeException;
import jg.ps.common.Utilities;
import jg.ps.common.precedent.PrecedentPresenter;
import jg.ps.parser.nodes.constructs.Legislation;
import net.percederberg.grammatica.parser.ParseException;
import net.percederberg.grammatica.parser.ParserCreationException;
import net.percederberg.grammatica.parser.ParserLogException;
import net.percederberg.grammatica.parser.Token;

/**
 * The front-end of the Polispeak parsing and validation ("cohesion") phase.
 * 
 * In the legislative process, this would be sort of like the 
 * drafting phase of a bill - hence the name "Drafter".
 * @author Jose
 *
 */
public class Drafter {
  
  private final String [] bills;
  
  /**
   * Constructs a Drafter
   * @param the paths of all Bills to check
   */
  public Drafter(String ... bills) {
    this.bills = bills;
  }
  
  /**
   * Drafts the bills constructed with this Drafter,
   * performing grammar, structure and type checks.
   * 
   * @param precedents - Precedents that the bills draw upon from
   * @return the verified Legislation ready for enforcement
   * 
   * @throws Exception - parser error , Legislation error due to validation
   */
  public Map<String, Legislation> draftLegislation(Map<String, PrecedentPresenter> precedents) throws Exception{
    HashMap<String, Legislation> allBills = new HashMap<>();
    
    //parse the bills first
    for (String billPath : bills) {
      //System.out.println("----PARSING: "+billPath);
      Legislation bill = parseBill(new File(billPath));
      allBills.put(bill.getName(), bill);
    }
    
    //construct the constitution, and other founding legislation
    for(PrecedentPresenter presenter : precedents.values()) {
      allBills.put(presenter.getRep().getName(), presenter.getRep());
    }
    
    //now continue with validating the submitted legislation  
    Validator validator = new Validator(allBills);
    
    //Flag for checking whether a incohesiveness has been found (exception encountered by Validator)
    boolean invalidFound = false;
    
    for(Legislation bill : allBills.values()) {
      if (!precedents.containsKey(bill.getName())) {
        //make sure to skip founding legislation!
        List<LegislativeException> exceptions = validator.validate(bill);
        if (!exceptions.isEmpty()) {
          System.err.println("  *** FOR THE BILL '"+bill.getName()+"', here are the faults we found: ");
          for (LegislativeException exception : exceptions) {
            System.err.println(exception.getMessage());
            invalidFound = true;
          }
        }
      }
    }
    
    if (invalidFound) {
      throw new RuntimeException("With errors found in your legislation...it dies in drafting. "
                               + "It doesn't even go to committee!");
    }
    
    TypeChecker checker = new TypeChecker(allBills);
    for(Legislation bill : allBills.values()) {
      if (!precedents.containsKey(bill.getName())) {
        //make sure to skip founding legislation!
        checker.check(bill);
      }
    }
    
    return allBills;
  }
  
  private Legislation parseBill(File path) throws ParseException, 
                                           LegislativeException, 
                                           FileNotFoundException, 
                                           ParserCreationException, 
                                           ParserLogException {
    ArrayList<Token> tokens = new ArrayList<>();
    
    PolispeakTokenizer tokenizer = new PolispeakTokenizer(new FileReader(path));   
    Token current = null;
    while ((current = tokenizer.next()) != null) {
      tokens.add(current);
    }
    
    //System.out.println("-------TOKENS FOR "+path.getName()+"----------");
    //tokens.forEach(x -> System.out.println(x));
    //System.out.println("-------TOKENS END FOR "+path.getName()+"----------");
    
    String fileName = Utilities.getBareFileName(path.getName());
    
    ResolutionBuilder billBuilder = new ResolutionBuilder(fileName);
    PolispeakParser parser = new PolispeakParser(null, billBuilder);
    parser.parseFromTokenList(tokens);
    
    return billBuilder.produceBill();
  }
}
