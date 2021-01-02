package jg.ps.parser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;

import jg.ps.parser.errors.BadConclusionException;
import jg.ps.parser.errors.BadSectionSequenceException;
import jg.ps.parser.errors.DuplicateDefinitionException;
import jg.ps.parser.errors.DuplicateSectionException;
import jg.ps.parser.nodes.Conditional;
import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.ExternalBillRef;
import jg.ps.parser.nodes.Invocation;
import jg.ps.parser.nodes.LocalVarDeclr;
import jg.ps.parser.nodes.atoms.FloatValue;
import jg.ps.parser.nodes.atoms.Identifier;
import jg.ps.parser.nodes.atoms.Instanciation;
import jg.ps.parser.nodes.atoms.Int;
import jg.ps.parser.nodes.atoms.Keyphrase;
import jg.ps.parser.nodes.atoms.NullValue;
import jg.ps.parser.nodes.atoms.Str;
import jg.ps.parser.nodes.atoms.Type;
import jg.ps.parser.nodes.constructs.Legislation;
import jg.ps.parser.nodes.constructs.Definition;
import jg.ps.parser.nodes.constructs.Section;
import jg.ps.parser.nodes.constructs.TypedVariable;
import net.percederberg.grammatica.parser.Node;
import net.percederberg.grammatica.parser.ParseException;
import net.percederberg.grammatica.parser.Production;
import net.percederberg.grammatica.parser.Token;

public class ResolutionBuilder extends PolispeakAnalyzer{
  
  private static final String CONSTITUTION = "The Constitution";

  protected final Stack<ArrayDeque<Expr>> stack;
  protected final Stack<Expr> actualNodes;
  
  private final String billName;
  
  public ResolutionBuilder(String billName) {
    this.billName = billName;
    this.stack = new Stack<>();
    this.actualNodes = new Stack<>();
  }
  
  public Legislation produceBill() {
    return (Legislation) actualNodes.pop();
  }
  
  //atoms START
  @Override
  protected void enterAtoms(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitAtoms(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    if (exprs.peekFirst() instanceof NullValue) {
      actualNodes.push(exprs.pollFirst());
    }
    else {
      //This atom is a primitive value. Remove it's type name.
      exprs.pollFirst();
      
      actualNodes.push(exprs.pollFirst());
    }
    
    return node;
  }
  
  @Override
  protected Node exitString(Token node) throws ParseException {
    String content = node.getImage();
    content = content.substring(1);
    content = content.substring(0, content.length() - 1);
    
    actualNodes.push(new Str(content, node.getStartLine(), node.getStartColumn()));
    
    return node;
  }
  
  @Override
  protected Node exitInteger(Token node) throws ParseException {
    actualNodes.push(new Int(Long.parseLong(node.getImage()), node.getStartLine(), node.getStartColumn()));
    return node;
  }
  
  @Override
  protected Node exitDouble(Token node) throws ParseException {
    actualNodes.push(new FloatValue(Double.parseDouble(node.getImage()), node.getStartLine(), node.getStartColumn()));
    return node;
  }
  
  @Override
  protected Node exitName(Token node) throws ParseException {
    actualNodes.push(new Identifier(node.getImage(), node.getStartLine(), node.getStartColumn()));
    return node;
  }
  
  @Override
  protected Node exitNull(Token node) throws ParseException {
    actualNodes.push(new NullValue(node.getStartLine(), node.getStartColumn()));
    return node;
  }
  
  @Override
  protected Node exitHyphen(Token node) throws ParseException {
    actualNodes.push(new Keyphrase(node, node.getStartLine(), node.getStartColumn()));
    return node;
  }
  //atoms END
  
  //atomic types START
  @Override
  protected Node exitIntPhrase(Token node) throws ParseException {
    actualNodes.push(new Type(node.getStartLine(), node.getStartColumn(), "Integer", CONSTITUTION));
    return node;
  }
  
  @Override
  protected Node exitFloatPhrase(Token node) throws ParseException {
    actualNodes.push(new Type(node.getStartLine(), node.getStartColumn(), "Float", CONSTITUTION));
    return node;
  }
  
  @Override
  protected Node exitStrPhrase(Token node) throws ParseException {
    actualNodes.push(new Type(node.getStartLine(), node.getStartColumn(), "String", CONSTITUTION));
    return node;
  }
  
  @Override
  protected Node exitUnknown(Token node) throws ParseException {
    actualNodes.push(new Type(node.getStartLine(), node.getStartColumn(), "Unknown", CONSTITUTION));
    return node;
  }
  //atomic types END
  
  //bill START
  @Override
  protected void enterBill(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitBill(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();

    Int resolutionNumber = (Int) exprs.pollFirst();
    Str resolutionTitle = (Str) exprs.pollFirst();
    Str resolutionDescription = (Str) exprs.pollFirst();
    
    LinkedHashMap<String, Section> sections = new LinkedHashMap<>();
    HashMap<String, Definition> definitions = new HashMap<>();
    
    for(Expr expr : exprs) {
      if (expr instanceof Section) {
        Section section = (Section) expr;
        
        if (section.getSectionNumber() != sections.size() + 1) {
          throw new BadSectionSequenceException(section.getSectionNumber(), 
                                                sections.size() + 1, 
                                                section.getLineNumber(), 
                                                section.getColumnNumber(), 
                                                billName);
        }
        else if (section.isTitled() && sections.containsKey(section.getSectionTitle())) {
          throw new DuplicateSectionException(section.getSectionTitle(), 
                                              section.getLineNumber(), 
                                              section.getColumnNumber(), 
                                              billName);
        }
        
        sections.put(section.isTitled() ? 
                     section.getSectionTitle() : 
                     String.valueOf(section.getSectionNumber()), section);
      }
      else if (expr instanceof Definition) {
        Definition definition = (Definition) expr;
        if (definitions.containsKey(definition.getName())) {
          //throw error
          throw new DuplicateDefinitionException(definition.getName(), 
                                                 definition.getLineNumber(), 
                                                 definition.getColumnNumber(), 
                                                 billName);
        }
        else {
          definitions.put(definition.getName(), definition);
        }
      }
    }
    
    Legislation bill = new Legislation(resolutionNumber.getActualValue().intValue(), 
                         resolutionTitle.getActualValue(), 
                         resolutionDescription.getActualValue(), 
                         sections.values().toArray(new Section[sections.size()]), 
                         definitions);
    
    actualNodes.push(bill);
    
    return node;
  }
  //bill END
  
  //definition START
  @Override
  protected void enterDefinitions(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitDefinitions(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    Identifier defName = (Identifier) exprs.pollFirst();
    
    LinkedHashMap<String, Type> members = new LinkedHashMap<>();
    
    while (!exprs.isEmpty()) {
      //remove hypen
      exprs.pollFirst();
      
      TypedVariable var = (TypedVariable) exprs.pollFirst();
      members.put(var.getVarName(), var.getType());
    }
    
    Definition definition = new Definition(defName.getLineNumber(), 
                                           defName.getColumnNumber(), 
                                           defName.getIdentifier(),
                                           members);
    actualNodes.push(definition);
    return node;
  }
  //definition END
  
  //section START
  @Override
  protected void enterSection(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitSection(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();

    //retrieve section number, and optionally its description
    Int secNumber =  ((Int) exprs.pollFirst());
    String secTitle = null;
    
    if (exprs.peekFirst() instanceof Keyphrase) {
      //hypen detected so there's a title
      exprs.pollFirst(); //remove hypen
      
      Str title = (Str) exprs.pollFirst();
      secTitle = title.getActualValue();
    }
    
    LinkedHashMap<String, Type> provisions = new LinkedHashMap<>();
    while (exprs.peekFirst() instanceof TypedVariable) {
      TypedVariable prov = (TypedVariable) exprs.pollFirst();
      provisions.put(prov.getVarName(), prov.getType());
    }
    
    Type resultType = null;
    if (exprs.peekFirst() instanceof Type) {
      resultType = (Type) exprs.pollFirst();
    }
    
    Expr [] body = new Expr[exprs.size() - 1];
    
    for(int i = 0; i < body.length; i++) {
      body[i] = exprs.pollFirst();
    }
    
    //the last expression in "exprs" is from the concluding statement of the section.
    //By our grammar, this expression is an Int.
    //Make sure this integer is equal to the section number
    Int concludeNum = (Int) exprs.pollFirst();
    if (!secNumber.getActualValue().equals(concludeNum.getActualValue())) {
      //throw error
      throw new BadConclusionException(secNumber.getActualValue().intValue(), 
                                       secNumber.getLineNumber(), 
                                       secNumber.getColumnNumber(), 
                                       secTitle);
    }
    
    Section section = new Section(secNumber.getLineNumber(), 
                                  secNumber.getColumnNumber(), 
                                  secNumber.getActualValue().intValue(), 
                                  secTitle, 
                                  provisions, 
                                  resultType, 
                                  body);
    
    actualNodes.push(section);
    
    return node;
  }
  
  @Override
  protected void enterParam(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitParam(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    Type paramType = (Type) exprs.pollFirst();
    Identifier paramName = (Identifier) exprs.pollFirst();
    
    TypedVariable typedVariable = new TypedVariable(paramName.getLineNumber(), 
                                                    paramName.getColumnNumber(), 
                                                    paramType, 
                                                    paramName.getIdentifier());
    actualNodes.push(typedVariable);
    return node;
  }
  //section END
  
  //expr START
  @Override
  protected void enterStep(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitStep(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    actualNodes.push(exprs.pollFirst());
    return node;
  }
  //expr END
  
  //conditional START
  @Override
  protected void enterConditionalStep(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitConditionalStep(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();

    Expr clause = exprs.pollFirst();
    Expr trueRoute = exprs.pollFirst();
    Expr falseRoute = exprs.pollFirst();;
    
    Conditional conditional = new Conditional(clause.getLineNumber(), 
                                              clause.getColumnNumber(), 
                                              clause, 
                                              trueRoute,
                                              falseRoute);
    actualNodes.push(conditional);
    return node;
  }
  //conditional END
  
  //instanciation START
  @Override
  protected void enterTypeInstantStep(Production node) throws ParseException {
    setEntrance();
    System.out.println("-----ENTER INSTAN. : "+actualNodes);
  }
  
  @Override
  protected Node exitTypeInstantStep(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    System.out.println("-----EXIT INSTAN. : "+exprs);
    
    Type type = (Type) exprs.pollFirst();
    
    Expr [] provisions = new Expr[exprs.size()];
    for (int i = 0; i < provisions.length; i++) {
      provisions[i] = exprs.pollFirst();
    }
    
    Instanciation instanciation = new Instanciation(type.getLineNumber(), 
                                                    type.getColumnNumber(), 
                                                    type, 
                                                    provisions);
    
    
    
    actualNodes.push(instanciation);
    
    return node;
  }
  //instanciation END
  
  //type description START
  @Override
  protected void enterTypeDescription(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitTypeDescription(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    String hostBill = billName;
    ExternalBillRef externalBillRef = null;
    if (exprs.peekFirst() instanceof ExternalBillRef) {
      externalBillRef = (ExternalBillRef) exprs.pollFirst();
    }
    
    String typeName = null;
    
    int line = -1;
    int col = -1;
    
    if (exprs.peekFirst() instanceof Type) {
      //this is a primitive type - defined in the Constitution
      Type primitive = (Type) exprs.pollFirst();
      typeName = primitive.getTypeName();
      hostBill = CONSTITUTION;
      
      line = primitive.getLineNumber();
      col = primitive.getColumnNumber();
    }
    else {
      //this is a custom type
      Identifier customType = (Identifier) exprs.pollFirst();
      typeName = customType.getIdentifier();
      hostBill = externalBillRef != null ? externalBillRef.getBillName() : billName;
      
      line = customType.getLineNumber();
      col = customType.getColumnNumber();
    }
    
    Type newType = new Type(line, col, typeName, hostBill);
    actualNodes.push(newType);
    
    return node;
  }
  //type description END
  
  //external bill START  
  @Override
  protected void enterExternalBill(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitExternalBill(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();

    Str externalBillName = (Str) exprs.pollFirst();
    
    actualNodes.push(new ExternalBillRef(externalBillName.getLineNumber(), 
                                         externalBillName.getColumnNumber(), 
                                         externalBillName.getString()));
    
    return node;
  }  
  //external bill END
  
  //invocation START
  @Override
  protected void enterFuncInvocStep(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitFuncInvocStep(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    String hostBill = billName;
    if (exprs.peekFirst() instanceof ExternalBillRef) {
      ExternalBillRef externalBillRef = (ExternalBillRef) exprs.pollFirst();
      hostBill = externalBillRef.getBillName();
    }
    
    Int sectionNumber = (Int) exprs.pollFirst();
    
    Expr [] provisions = exprs.toArray(new Expr[exprs.size()]);
    
    Invocation invocation = new Invocation(sectionNumber.getLineNumber(), 
                                           sectionNumber.getColumnNumber(), 
                                           sectionNumber.getActualValue().intValue(), 
                                           hostBill, 
                                           provisions);
    actualNodes.push(invocation);
    return node;
  }
  //invocation END
  
  //Local variable START
  
  @Override
  protected void enterVarStep(Production node) throws ParseException {
    setEntrance();
  }
  
  @Override
  protected Node exitVarStep(Production node) throws ParseException {
    ArrayDeque<Expr> exprs = exitEntrance();
    
    Identifier varName = (Identifier) exprs.pollFirst();
    Type varType = (Type) exprs.pollFirst();
    Expr initValue = exprs.pollFirst();
    
    LocalVarDeclr localVar = new LocalVarDeclr(varName.getLineNumber(), 
                                               varName.getColumnNumber(), 
                                               varName.getIdentifier(), 
                                               varType, 
                                               initValue, 
                                               exprs.toArray(new Expr[exprs.size()]));
    
    actualNodes.push(localVar);
    return node;
  }
  
  //Local variable END
  
  //HELPER methods start
  private void setEntrance(){
    stack.add(new ArrayDeque<>());
    actualNodes.push(null); //add marker
  }

  private ArrayDeque<Expr> exitEntrance(){
    ArrayDeque<Expr> latest = stack.pop();

    while (actualNodes.peek() != null) {
      latest.addFirst(actualNodes.pop());
    }

    actualNodes.pop(); //removes marker

    return latest;
  }
  //HELPER methods end
}
