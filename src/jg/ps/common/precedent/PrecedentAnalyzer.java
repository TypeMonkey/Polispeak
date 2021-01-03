package jg.ps.common.precedent;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import jg.ps.enforcement.instances.Instance;
import jg.ps.parser.nodes.Expr;
import jg.ps.parser.nodes.atoms.Type;
import jg.ps.parser.nodes.constructs.Definition;
import jg.ps.parser.nodes.constructs.Legislation;
import jg.ps.parser.nodes.constructs.Section;

public class PrecedentAnalyzer {

  private static final Expr [] EMPTY_BODY = new Expr[0]; //useful when creating sections
  
  public static Map<String, PrecedentPresenter> analyzePrecedents(Class<?> ... classes) {
    HashMap<String, PrecedentPresenter> presenters = new HashMap<>();
    
    //make presenters for the precedents
    for (Class<?> target : classes) {
      PrecedentPresenter presenter = createPresenter(target);
      if (presenter != null) {
        presenters.put(presenter.getRep().getName(), presenter);
      }
    }
    
    //now check to make sure all types used in precedents exists
    for(PrecedentPresenter presenter : presenters.values()) {
      Legislation legislation = presenter.getRep();
      
      //check definition types
      
      //check section provisions and fulfillment
    }
    
    return presenters;
  }
  
  private static PrecedentPresenter createPresenter(Class<?> presenter) {
    if (presenter.isAnnotationPresent(Precedent.class)) {
      Precedent precInfo = presenter.getAnnotation(Precedent.class);
      
      final String TITLE = precInfo.title();
      final String DESC = precInfo.desc();
      
      //Analyze Sections
      
      class SectionTuple{ 
        final Section section; final Method method; 
        
        public SectionTuple(Section section, Method method) {
          this.section = section;
          this.method = method;
        }
      }
      
      HashMap<Integer, SectionTuple> sections = new HashMap<>();
      
      int maxSecID = 0;
      
      for(Method method : presenter.getDeclaredMethods()) {
        if (method.isAnnotationPresent(PrecSection.class)) {      
          PrecSection sectionInfo = method.getAnnotation(PrecSection.class);
          
          if (!Modifier.isStatic(method.getModifiers()) || 
              !Modifier.isPublic(method.getModifiers())) {
            throw new RuntimeException("For the precedent '"+presenter.getSimpleName()+
                ", Section "+sectionInfo.num()+" , the corresponding method must be static and public");
          }
          
          Section section = makeSection(method, sectionInfo, presenter.getSimpleName());          
                  
          if (sections.containsKey(section.getSectionNumber())) {
            //all sections must have unique keys
            throw new RuntimeException("For the precedent '"+presenter.getSimpleName()+
                                       " there already exists a Section "+section.getSectionNumber());
          }
          else if (section.getSectionNumber() < 1) {
            //The least valued Section must be 1
            throw new RuntimeException("For the precedent '"+presenter.getSimpleName()+
                                       ", Section "+section.getSectionNumber()+" is invalidly numbered");
          }
          
          maxSecID = section.getSectionNumber() > maxSecID ? section.getSectionNumber() : maxSecID;
          
          sections.put(section.getSectionNumber(), new SectionTuple(section, method));
        }
      }
      
      if (maxSecID != sections.size()) {
        //Sections aren't numbered correctly
        throw new RuntimeException("For the precedent '"+presenter.getSimpleName()+
                                   "', the sections aren't numbered correctly. "+
                                   "The first Section should be numbered 1 and so on....");
      }
      
      Section [] orderedSections = new Section[sections.size()];
      Method [] orderedMethods = new Method[sections.size()];
      
      for(SectionTuple sec : sections.values()) {
        orderedSections[sec.section.getSectionNumber() - 1] = sec.section;
        orderedMethods[sec.section.getSectionNumber() - 1] = sec.method;
      }
      
      //Analyze definitions
      
      HashMap<String, Definition> definitions = new HashMap<>();
      HashMap<String, Class<?>> classes = new HashMap<>();
      
      for(Class<?> def : presenter.getDeclaredClasses()) {
        if (def.isAnnotationPresent(PrecDefinition.class)) {
          PrecDefinition defInfo = def.getAnnotation(PrecDefinition.class);

          if (!Modifier.isStatic(def.getModifiers()) || 
              !Modifier.isPublic(def.getModifiers())) {
            throw new RuntimeException("For the precedent '"+presenter.getSimpleName()+
                                       ", the class corresponding to the definition "+
                                       def.getSimpleName()+" must be static and public");
          }
          
          Definition fullDef = makeDefinition(def, defInfo, presenter.getSimpleName());
          definitions.put(fullDef.getName(), fullDef);
          classes.put(def.getSimpleName(), def);
        }
      }
      
      Legislation legislation = new Legislation(1, TITLE, DESC, orderedSections, definitions);
      return new PrecedentPresenter(legislation, orderedMethods, classes);
    }
    
    return null;
  }
  
  private static Definition makeDefinition(Class<?> def, PrecDefinition defInfo, String host) {   
    final String DEF_NAME = def.getSimpleName();
    
    LinkedHashMap<String, Type> members = new LinkedHashMap<>();
    
    for(Field member : def.getFields()) {
      if (member.isAnnotationPresent(PrecMember.class)) {
        
        PrecMember annotated = member.getAnnotation(PrecMember.class);
        
        if (Modifier.isStatic(member.getModifiers())) {
          throw new RuntimeException("For the precedent '"+host+
              ", the class corresponding to the definition "+
              def.getSimpleName()+" has a static member '"+member.getName()+"'");
        }
        
        Type parsedType = parseType(annotated.type());
        if (parsedType == null) {
          //bad Type! throw error
          throw new RuntimeException("For the precedent '"+host+
              ", the class corresponding to the definition "+
              def.getSimpleName()+" has a static member '"+member.getName()+"'"+
              " of type '"+annotated.type()+"' which is invalid");
        }
        
        if (!Instance.class.isAssignableFrom(member.getType())) {
          throw new RuntimeException("For the precedent '"+host+
              ", the class corresponding to the definition "+
              def.getSimpleName()+" has a member '"+member.getName()+"'"+
              " of type '"+annotated.type()+"' which isn't an Instance");
        }
        
        members.put(member.getName(), parsedType);
      }
    }
    
    if (def.getConstructors().length == 1) {
      Constructor<?> soleConstructor = def.getConstructors()[0];
      
      if (soleConstructor.getParameterCount() != members.size()) {
        throw new RuntimeException("For the precedent '"+host+
            ", the class corresponding to the definition "+
            def.getSimpleName()+" has "+soleConstructor.getParameterCount()+" constructor parameters"+
            " but the class itself has only "+members.size()+" members");
      }
      
      //Now check that all parameters have Instance as type
      for(Class<?> ptype : soleConstructor.getParameterTypes()) {
        if (Instance.class != ptype) {
          throw new RuntimeException("For the precedent '"+host+
              ", the class corresponding to the definition "+
              def.getSimpleName()+" has a "+ptype.getName()+" as a constructor parameter"+
              " when it should all be "+Instance.class.getCanonicalName());
        }
      }
    }
    
    return new Definition(-1, -1, def.getSimpleName(), members);
  }

  
  private static Section makeSection(Method method, PrecSection sectionInfo, String host) {    
    final int secNum = sectionInfo.num();
    final String secTitle = sectionInfo.title().isEmpty() ? sectionInfo.title() : null;
    final Type returnType = parseType(sectionInfo.fulfillment());
    
    if (returnType == null) {
      //bad Type! throw error
      throw new RuntimeException("For the precedent '"+host+
                                 "', Section "+secNum+
                                 ", the return type '"+
                                 sectionInfo.fulfillment()+"' is invalid.");
    }
    
    LinkedHashMap<String, Type> provisions = new LinkedHashMap<>();
    int provNum = 0;
    for(String provType : sectionInfo.provisions()) {
      Type actType = parseType(provType);
      if (actType == null) {
        //bad Type! throw error
        throw new RuntimeException("For the precedent '"+host+
                                   "', Section "+secNum+
                                   ", the provision type '"+
                                   provType+"' is invalid.");
      }
      
      provisions.put("Prov"+provNum, actType);
      provNum++;
    }
    
    if (sectionInfo.provisions().length != method.getParameterCount()) {
      throw new RuntimeException("For the precedent '"+host+
          "', Section "+secNum+
          ", there are "+sectionInfo.provisions().length+" provisions declared, but "+
          "the corresponding method has "+method.getParameterCount()+" parameters.");
    }
    
    //Now check that all parameters have Instance as type
    for(Class<?> ptype : method.getParameterTypes()) {
      if (!Instance.class.isAssignableFrom(ptype)) {
        throw new RuntimeException("For the precedent '"+host+
            "', Section "+secNum+
            ". the corresponding method has a parameter type of'"+
            ptype.getName()+"' which isn't a subclass of Instance.");
      }
    }
    
    //check the return type
    if (!Instance.class.isAssignableFrom(method.getReturnType())) {
      throw new RuntimeException("For the precedent '"+host+
          "', Section "+secNum+
          ". the corresponding method has a return type of'"+
          method.getReturnType().getName()+"' which isn't a subclass of Instance.");
    }
    
    Section section = new Section(-1, -1, secNum, secTitle, provisions, returnType, EMPTY_BODY);
    return section;
  }
  
  private static Type parseType(String rawType) {
    String [] split = rawType.trim().split("\\$");
    if (split.length != 2) {
      //Type name has to include dollar sign
      return null;
    }
    else if(split[0].isEmpty()){
      return new Type(-1, -1, split[1], "The Constitution");
    }
    else {
      return new Type(-1, -1, split[1], split[0]);
    }
  }
}
