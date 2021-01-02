package jg.ps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jg.ps.enforcement.VirtualSection;
import jg.ps.enforcement.instances.Instance;
import jg.ps.enforcement.instances.StringInstance;
import jg.ps.enforcement.instances.primitives.FloatInstance;
import jg.ps.enforcement.instances.primitives.IntValue;
import jg.ps.enforcement.instances.primitives.Null;
import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.FloatValue;
import jg.ps.parser.nodes.atoms.Int;
import jg.ps.parser.nodes.atoms.Type;
import jg.ps.parser.nodes.constructs.Legislation;
import jg.ps.parser.nodes.constructs.Definition;
import jg.ps.parser.nodes.constructs.Section;

/**
 * Ya'll already know what this is about...
 * 
 * The founding document of a nation...
 * The SUPREME LAW OF THE LAND...
 * 
 * THE CONSTITUTION!
 * 
 * For Polispeak, "The Constitution" is a collection of definitions
 * and invocable sections that can be used by any other legislation.
 * 
 * In terms of software dev. speak, it holds the standard functions
 * and "classes" of the Polispeak language
 * 
 * @authors Alexander Hamilton (through Lin-Manuel Miranda)
 *          Thomas Jefferson
 *          James Madison
 *          John Adams
 *          Jose Guaro (digital rendering)
 */
public class TheConstitution {

  private static TheConstitution instance;
  
  private Legislation constiBill;
  private Legislation stringBill;
  
  private List<VirtualSection> constiVSections;
  private List<VirtualSection> stringBillVSections;
  
  private TheConstitution() {}
  
  private void initStringBillVSections(Legislation constitution) {
    stringBillVSections = new ArrayList<>();
    
    //Concatenation Law: String + String
    stringBillVSections.add(new VirtualSection(constitution.getName(), 1) {
      @Override
      public Instance fulfill(Instance... provisions) {
        StringInstance left = (StringInstance) provisions[0];
        StringInstance right = (StringInstance) provisions[1];
        return new StringInstance(left.getValue() + right.getValue());
      }
    });
    
    //Substring Law: 
    stringBillVSections.add(new VirtualSection(constitution.getName(), 2) {
      @Override
      public Instance fulfill(Instance... provisions) {
        StringInstance target = (StringInstance) provisions[0];
        
        IntValue start = (IntValue) provisions[1];
        IntValue end = (IntValue) provisions[2];
        
        return new StringInstance(target.getValue().substring(start.getValue().intValue(), 
                                                              end.getValue().intValue()));
      }
    });
    
    //Substring Existence Law: 
    stringBillVSections.add(new VirtualSection(constitution.getName(), 3) {
      @Override
      public Instance fulfill(Instance... provisions) {
        StringInstance target = (StringInstance) provisions[0];
        StringInstance toFind = (StringInstance) provisions[1];

        long result = target.getValue().contains(toFind.getValue()) ? 1 : 0;
        
        return new IntValue(result);
      }
    });
    
    //Character Law: 
    stringBillVSections.add(new VirtualSection(constitution.getName(), 4) {
      @Override
      public Instance fulfill(Instance... provisions) {
        StringInstance target = (StringInstance) provisions[0];        
        IntValue toFind = (IntValue) provisions[1];

        long targetSize = target.getValue().length();
        long index = toFind.getValue();
        
        if (index >= 0 && index < targetSize) {
          return new StringInstance(String.valueOf(target.getValue().charAt((int) index)));
        }
        else {
          return Null.getNullInstance();
        }       
      }
    });
    
    //Emptiness Law: 
    stringBillVSections.add(new VirtualSection(constitution.getName(), 5) {
      @Override
      public Instance fulfill(Instance... provisions) {
        StringInstance target = (StringInstance) provisions[0];        

        long result = target.getValue().length() == 0 ? 1 : 0;
        return new IntValue(result);
      }
    });
    
    //Transformation Law: Integer
    stringBillVSections.add(new VirtualSection(constitution.getName(), 6) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue target = (IntValue) provisions[0];        

        return new StringInstance(String.valueOf(target.getValue()));
      }
    });
    
    //Transformation Law: Float
    stringBillVSections.add(new VirtualSection(constitution.getName(), 7) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance target = (FloatInstance) provisions[0];        

        return new StringInstance(String.valueOf(target.getValue()));
      }
    });
    
  }

  
  private void initStringBill() {
    /*
     * The Bill of Strings
     * 
     * - A collection of essential procedures to manipulate
     *   and characterize String objects
     *   
     * -> Section 1: The Concatenation Law ,       String + String => String
     * -> Section 2: The Substring Law,            String , Integer, Integer => String
     * -> Section 3: The Substring Existence Law,  String , String => Integer
     * -> Section 4: The Character Law,            String, Integer => String
     * -> Section 5: The Emptiness Law             String => Integer
     * 
     * -> Section 6: Transformation Procedure     Integer => String
     * -> Section 7: Transformation Procedure     Float => String 
     */
    
    ArrayList<Section> allSections = new ArrayList<>();
    
    allSections.add(createDummySection(1, "The Concatenation Law", Type.STR_TYPE, Type.STR_TYPE, Type.STR_TYPE));
    allSections.add(createDummySection(2, "The Substring Law", Type.STR_TYPE, Type.STR_TYPE, Type.INT_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(3, "The Substring Existence Law", Type.INT_TYPE, Type.STR_TYPE, Type.STR_TYPE));
    allSections.add(createDummySection(4, "The Character Law", Type.STR_TYPE, Type.STR_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(5, "The Emptiness Law", Type.INT_TYPE, Type.STR_TYPE));

    allSections.add(createDummySection(6, "Transformation Procedure", Type.STR_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(7, "Transformation Procedure", Type.STR_TYPE, Type.FLOAT_TYPE));
    
    stringBill = new Legislation(1, 
                                 "The Bill of Strings", 
                                 "A collection of essential procedures to manipulate and characterize String objects", 
                                 allSections.toArray(new Section[allSections.size()]), 
                                 new HashMap<>());
  }
  
  private void initConstitutionVSections(Legislation constitution) {
    constiVSections = new ArrayList<>();
        
    //ADDITION LAW: int + int
    constiVSections.add(new VirtualSection(constitution.getName(), 1) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        IntValue right = (IntValue) provisions[1];
        return new IntValue(left.getValue() + right.getValue());
      }
    });
    
    //ADDITION LAW: float + float
    constiVSections.add(new VirtualSection(constitution.getName(), 2) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        FloatInstance right = (FloatInstance) provisions[1];
        return new FloatInstance(left.getValue() + right.getValue());
      }
    });
    
    //ADDITION LAW: int + float
    constiVSections.add(new VirtualSection(constitution.getName(), 3) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        FloatInstance right = (FloatInstance) provisions[1];
        return new FloatInstance(left.getValue() + right.getValue());
      }
    });
    
    //ADDITION LAW: float + int
    constiVSections.add(new VirtualSection(constitution.getName(), 4) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        IntValue right = (IntValue) provisions[1];
        return new FloatInstance(left.getValue() + right.getValue());
      }
    });
    
    ///////////////////////////////////////
    
    //Multiplication LAW: int + int
    constiVSections.add(new VirtualSection(constitution.getName(), 5) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        IntValue right = (IntValue) provisions[1];
        return new IntValue(left.getValue() * right.getValue());
      }
    });
    
    //Multiplication LAW: float + float
    constiVSections.add(new VirtualSection(constitution.getName(), 6) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        FloatInstance right = (FloatInstance) provisions[1];
        return new FloatInstance(left.getValue() * right.getValue());
      }
    });
    
    //Multiplication LAW: int + float
    constiVSections.add(new VirtualSection(constitution.getName(), 7) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        FloatInstance right = (FloatInstance) provisions[1];
        return new FloatInstance(left.getValue() * right.getValue());
      }
    });
    
    //Multiplication LAW: float + int
    constiVSections.add(new VirtualSection(constitution.getName(), 8) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        IntValue right = (IntValue) provisions[1];
        return new FloatInstance(left.getValue() * right.getValue());
      }
    });
    
    ///////////////////////////////
    
    //DIVISION LAW: int + int
    constiVSections.add(new VirtualSection(constitution.getName(), 9) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        IntValue right = (IntValue) provisions[1];
        return new FloatInstance((double) (left.getValue() / right.getValue()));
      }
    });
    
    //DIVISION LAW: float + float
    constiVSections.add(new VirtualSection(constitution.getName(), 10) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        FloatInstance right = (FloatInstance) provisions[1];
        return new FloatInstance(left.getValue() / right.getValue());
      }
    });
    
    //DIVISION LAW: int + float
    constiVSections.add(new VirtualSection(constitution.getName(), 11) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        FloatInstance right = (FloatInstance) provisions[1];
        return new FloatInstance(left.getValue() / right.getValue());
      }
    });
    
    //DIVISION LAW: float + int
    constiVSections.add(new VirtualSection(constitution.getName(), 12) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        IntValue right = (IntValue) provisions[1];
        return new FloatInstance(left.getValue() / right.getValue());
      }
    });
    
    /////////////////////////////
    
    //NEGATION LAW: int
    constiVSections.add(new VirtualSection(constitution.getName(), 13) {
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue left = (IntValue) provisions[0];
        return new IntValue(-left.getValue());
      }
    });
    
    //NEGATION LAW: float
    constiVSections.add(new VirtualSection(constitution.getName(), 14) {
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance left = (FloatInstance) provisions[0];
        return new FloatInstance(-left.getValue());
      }
    });
    
    ///////////////////////
    
    //Introspection Law
    constiVSections.add(new VirtualSection(constitution.getName(), 15) {
      @Override
      public Instance fulfill(Instance... provisions) {
        return new StringInstance(provisions[0].getType());
      }
    });
    
    ///////////////////////
    
    //Equality Law
    constiVSections.add(new VirtualSection(constitution.getName(), 16) {     
      @Override
      public Instance fulfill(Instance... provisions) {
        Instance left = provisions[0];
        Instance right = provisions[1];
        
        long boolResult = left.getType().equals(right.getType()) && left.equals(right) ? 1 : 0;
        
        return new IntValue(boolResult);
      }
    });
    
    ////////////////////////
    
    //Negative Law : int
    constiVSections.add(new VirtualSection(constitution.getName(), 17) {     
      @Override
      public Instance fulfill(Instance... provisions) {
        IntValue value = (IntValue) provisions[0];
        
        long boolResult = value.getValue() < 0 ? 1 : 0;
        
        return new IntValue(boolResult);
      }
    });
    
    //Negative Law : float
    constiVSections.add(new VirtualSection(constitution.getName(), 18) {     
      @Override
      public Instance fulfill(Instance... provisions) {
        FloatInstance value = (FloatInstance) provisions[0];
        
        long boolResult = value.getValue() < 0 ? 1 : 0;
        
        return new IntValue(boolResult);
      }
    });
    
    //////////////////
    
    //IO Principle : Input
    constiVSections.add(new VirtualSection(constitution.getName(), 19) {     
      @Override
      public Instance fulfill(Instance... provisions) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        String input = null;
        try {
          input = reader.readLine();
        } catch (IOException e) {
          System.err.println("----IO ERROR AT INPUT????? ");
        }
                
        return input == null ? Null.getNullInstance() : new StringInstance(input);
      }
    });
    
    //IO Principle : Output
    constiVSections.add(new VirtualSection(constitution.getName(), 20) {     
      @Override
      public Instance fulfill(Instance... provisions) {
        StringInstance toprint = (StringInstance) provisions[0];
        
        System.out.println(toprint.getValue());
        
        return toprint;
      }
    });
  }
  
  private void initConstitution() {    
    final LinkedHashMap<String, Type> EMPTY_MAP = new LinkedHashMap<>(); 
    
    /*
     * Definitions:
     * - Integer
     * - Float
     * - Unknown 
     * - String
     *   -> Only one component, which is length
     */
    Definition intDef = new Definition(-1, -1, "Integer", EMPTY_MAP);
    Definition floatDef = new Definition(-1, -1, "Float", EMPTY_MAP);
    Definition unknownDef = new Definition(-1, -1, "Unknown", EMPTY_MAP);
    
    LinkedHashMap<String, Type> stringMembers = new LinkedHashMap<>();
    stringMembers.put("length", Type.INT_TYPE);
    Definition stringDef = new Definition(-1, -1, "String", stringMembers);
    
    HashMap<String, Definition> defs = new HashMap<>();
    defs.put(intDef.getName(), intDef);
    defs.put(floatDef.getName(), floatDef);
    defs.put(unknownDef.getName(), unknownDef);
    defs.put(stringDef.getName(), stringDef);
    
    /*
     * Sections:
     * - Article 1: The Addition Law
     *      -> Section 1: int + int     => int
     *      -> Section 2: float + float => float
     *      -> Section 3: int + float   => float
     *      -> Section 4: float + int   => float
     * - Article 2: The Multiplication Law
     *      -> Section 5: int * int     => int
     *      -> Section 6: float * float => float
     *      -> Section 7: int * float   => float
     *      -> Section 8: float * int   => float
     * - Article 3: The Division Law
     *      -> Section 9 : int / int     => float
     *      -> Section 10: float / float => float
     *      -> Section 11: int / float   => float
     *      -> Section 12: float / int   => float
     * - Article 4: The Negation Law
     *      -> Section 13: int
     *      -> Section 14: float
     * - Article 5: Right to Introspection (returns the type (as a string) of a object)
     *      -> Section 15: Unknown
     * - Article 6: Equality Among All Objects
     *      -> Section 16: Unknown == Unknown
     * - Article 7: Comparison Procedure
     *      -> Section 17: Is Negative? -> int
     *      -> Section 18: Is Negative? -> float
     * - Article 8: The IO Principle
     *      -> Section 19: Input => String
     *      -> Section 20: Output -> String => String
     */
    
    ArrayList<Section> allSections = new ArrayList<>();

    allSections.add(createDummySection(1, "The Addition Law", Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(2, "The Addition Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE));
    allSections.add(createDummySection(3, "The Addition Law", Type.FLOAT_TYPE, Type.INT_TYPE, Type.FLOAT_TYPE));
    allSections.add(createDummySection(4, "The Addition Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE));

    allSections.add(createDummySection(5, "The Multiplication Law", Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(6, "The Multiplication Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE));
    allSections.add(createDummySection(7, "The Multiplication Law", Type.FLOAT_TYPE, Type.INT_TYPE, Type.FLOAT_TYPE));
    allSections.add(createDummySection(8, "The Multiplication Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE));
    
    allSections.add(createDummySection(9, "The Division Law", Type.FLOAT_TYPE, Type.INT_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(10, "The Division Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE));
    allSections.add(createDummySection(11, "The Division Law", Type.FLOAT_TYPE, Type.INT_TYPE, Type.FLOAT_TYPE));
    allSections.add(createDummySection(12, "The Division Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE));
    
    allSections.add(createDummySection(13, "The Negation Law", Type.INT_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(14, "The Negation Law", Type.FLOAT_TYPE, Type.FLOAT_TYPE));

    allSections.add(createDummySection(15, "Introspection Principle", Type.STR_TYPE, Type.UNKNOWN_TYPE));

    allSections.add(createDummySection(16, "Equality Principle", Type.INT_TYPE, Type.UNKNOWN_TYPE, Type.UNKNOWN_TYPE));

    allSections.add(createDummySection(17, "The Negation Law", Type.INT_TYPE, Type.INT_TYPE));
    allSections.add(createDummySection(18, "The Negation Law", Type.INT_TYPE, Type.FLOAT_TYPE));
    
    allSections.add(createDummySection(19, "The IO Principle", Type.STR_TYPE, new Type[0]));
    allSections.add(createDummySection(20, "The IO Principle", Type.STR_TYPE, Type.STR_TYPE));
    
    constiBill = new Legislation(0, 
                    "The Constitution", 
                    "Collection of definitions and sections that lay the foundations of all laws", 
                    allSections.toArray(new Section[allSections.size()]), 
                    defs);
  }
  
  private Section createDummySection(int secNum, String title, Type result, Type ... provisions) {
    final Expr [] EMPTY_BODY = new Expr[0];
    
    LinkedHashMap<String, Type> autoProvisions = new LinkedHashMap<>();
    for(int i = 1; i <= provisions.length; i++) {
      autoProvisions.put("Con"+i, provisions[i-1]);
    }
    
    return new Section(-1, -1, secNum, title, autoProvisions, result, EMPTY_BODY);
  }
  
  public Legislation getConstitution() {
    return constiBill;
  }
  
  public Legislation getStringBill() {
    return stringBill;
  }
  
  public List<VirtualSection> getConstiVSections() {
    return constiVSections;
  }
  
  public List<VirtualSection> getStringBillVSections() {
    return stringBillVSections;
  }
  
  public static TheConstitution createTheConstitution() {
    if (instance == null) {
      instance = new TheConstitution();
      
      instance.initConstitution();
      instance.initConstitutionVSections(instance.constiBill);
      
      instance.initStringBill();
      instance.initStringBillVSections(instance.stringBill);
    }
    return instance;
  }
}
