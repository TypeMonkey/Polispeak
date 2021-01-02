package jg.ps.parser;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import jg.ps.TheConstitution;
import jg.ps.cohesion.TypeChecker;
import jg.ps.cohesion.Validator;
import jg.ps.common.LegislativeException;
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
  
  public Map<String, Legislation> draftLegislation(){
    HashMap<String, Legislation> allBills = new HashMap<>();
    
    //parse the bills first
    for (String billPath : bills) {
      try {
        System.out.println("----PARSING: "+billPath);
        Legislation bill = parseBill(new File(billPath));
        allBills.put(getBareFileName(billPath), bill);
      } catch (LegislativeException e) {
        System.err.print(e.getMessage());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    //construct the constitution, and other founding legislation
    HashSet<String> foundingLegislation = new HashSet<>();
    
    TheConstitution theConstitution = TheConstitution.createTheConstitution();
    Legislation constitution = theConstitution.getConstitution();
    Legislation stringBill = theConstitution.getStringBill();
    if (allBills.containsKey(constitution.getName())) {
      throw new RuntimeException("Oh no! There's a legislation claiming to be 'The Constituion'. Please remove it.");
    }
    if (allBills.containsKey(stringBill.getName())) {
      throw new RuntimeException("Oh no! There's a legislation claiming to be 'The Bill of Strings'. Please remove it.");
    }
    
    allBills.put(constitution.getName(), constitution);
    allBills.put(stringBill.getName(), stringBill);
    
    foundingLegislation.add(constitution.getName());
    foundingLegislation.add(stringBill.getName());
    
    //now continue with validating the submitted legislation  
    Validator validator = new Validator(allBills);
    
    //Flag for checking whether a incohesiveness has been found (exception encountered by Validator)
    boolean invalidFound = false;
    
    for(Legislation bill : allBills.values()) {
      if (!foundingLegislation.contains(bill.getName())) {
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
      if (!foundingLegislation.contains(bill.getName())) {
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
    
    System.out.println("-------TOKENS FOR "+path.getName()+"----------");
    tokens.forEach(x -> System.out.println(x));
    System.out.println("-------TOKENS END FOR "+path.getName()+"----------");
    
    ResolutionBuilder billBuilder = new ResolutionBuilder(getBareFileName(path.getName()));
    PolispeakParser parser = new PolispeakParser(null, billBuilder);
    parser.parseFromTokenList(tokens);
    
    Legislation bill = billBuilder.produceBill();
    return bill;
  }
  
  /**
   * Returns the name of a file, without its extension
   * @param rawFileName - the name of the file, potentially having its extension
   * @return the name of a file, without its extension
   */
  public static String getBareFileName(String rawFileName) {
    int dotIndex = rawFileName.indexOf('.');
    if (dotIndex == -1) {
      return rawFileName;
    }
    else {
      return rawFileName.substring(0, dotIndex);
    }
  }
}
