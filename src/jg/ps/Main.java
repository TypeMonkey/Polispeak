package jg.ps;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jg.ps.common.BillOfStrings;
import jg.ps.common.TheConstitution;
import jg.ps.common.precedent.PrecedentAnalyzer;
import jg.ps.common.precedent.PrecedentPresenter;
import jg.ps.enforcement.Executive;
import jg.ps.enforcement.instances.Instance;
import jg.ps.parser.Drafter;
import jg.ps.parser.nodes.constructs.Legislation;

/**
 * Entry-point for the Polispeak Interpreter
 * @author Jose
 */
public class Main {
  
  /**
   * Convenient class that couples inputed files
   * together, such as precedents and sources.
   * @author Jose
   */
  public static class CLInput{
    
    private final String entrySource;
    private final String precDir;
    private final String [] sources;
    
    /**
     * Constructs a CLInput
     * @param start - the path of the legislation to start interpretation at
     * @param precedents - the paths of precedents
     * @param sources - the paths of the legislation needed for interpret
     */
    public CLInput(String entrySource, String precDir, String [] sources) {
      this.entrySource = entrySource;
      this.precDir = precDir;
      this.sources = sources;
    }
    
    public String getEntrySource() {
      return entrySource;
    }
    
    public String getPrecedentDir() {
      return precDir;
    }
    
    public String[] getSources() {
      return sources;
    }
  }

  /**
   * Driver method for the Polispeak Interpreter
   * @param args - command line arguments to parse
   * @throws Exception - if an exception occurs at any stage of interpretation
   */
  public static void main(String [] args) throws Exception {
    //System.out.println("---POLISPEAK v1.0--- ");
    
    CLInput arguments = parseCL(args);
    if (arguments != null) {
      
      Map<String, PrecedentPresenter> precedents = new HashMap<>();
      if (arguments.getPrecedentDir() != null) {
        precedents.putAll(analyzePrecedents(arguments.getPrecedentDir()));
      }
      
      //Prepare the built-in precedents: "The Constitution" and "The Bill of Strings"
      Map<String, PrecedentPresenter> builtInPrecedents = PrecedentAnalyzer.analyzePrecedents(TheConstitution.class, 
                                                                                              BillOfStrings.class);
      final PrecedentPresenter BUILT_IN_CONSTITUTION = builtInPrecedents.get("The Constitution");
      final PrecedentPresenter BUILT_IN_BILLSTRINGS = builtInPrecedents.get("The Bill of Strings");
      
      //WARN USER if there's precedent called "The Constitution" that's not jg.ps.common.TheConsitution
      if (precedents.containsKey("The Constitution") && 
          precedents.get("The Constitution").getBackingClass() != TheConstitution.class) {
        System.err.println("WARNING: The Constitution being used isn't the one that was drafted by the Founding Fathers!");
      }
      else {
        precedents.put(BUILT_IN_CONSTITUTION.getRep().getName(), BUILT_IN_CONSTITUTION);
      }
      
      //WARN USER if there's precedent called "The Bill of Strings" that's not jg.ps.common.BillOfStrings
      if (precedents.containsKey("The Bill of Strings") && 
          precedents.get("The Bill of Strings").getBackingClass() != BillOfStrings.class) {
        System.err.println("WARNING: The Bill of Strings being used isn't the one that was drafted by me!");
      }
      else {
        precedents.put(BUILT_IN_BILLSTRINGS.getRep().getName(), BUILT_IN_BILLSTRINGS);
      }
      
      Drafter drafter = new Drafter(arguments.getSources());
      Map<String, Legislation> draftedLegis = drafter.draftLegislation(precedents);
      
      if (!draftedLegis.containsKey(arguments.getEntrySource())) {
        throw new RuntimeException("Cannot find the starting legislation '"+arguments.getEntrySource()+"'. Exiting....");
      }
      
      Executive executive = new Executive(draftedLegis, precedents);
      executive.enforceLegislation(draftedLegis.get(arguments.getEntrySource()), new Instance[0]);
    }
  }
  
  /**
   * Load the precedent legislations with their respective .class files
   * @param directory - the directory containing the precedents
   * @return a mapping between the names of the precedents and the actual PrecedentPresenters
   * @throws Exception - if any exeception occurs during the loading process
   */
  public static Map<String, PrecedentPresenter> analyzePrecedents(String directory) throws Exception {
    File dirPath = new java.io.File(directory);
    
    /*
     * Consider only .class files
     */
    File [] precedents = dirPath.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(".class") && pathname.canRead();
      }
    });
    
    
    URL [] precURLs = new URL[precedents.length];
    for (int i = 0; i < precedents.length; i++) {
      precURLs[i] = precedents[i].toURI().toURL();
    }    
    
    URLClassLoader classLoader = new URLClassLoader(precURLs);
    
    Class<?> [] classFiles = new Class<?>[precedents.length];
    for (int i = 0; i < precedents.length; i++) {
      classFiles[i] = classLoader.loadClass(precedents[i].getName());
    }
    
    classLoader.close();
    
    return PrecedentAnalyzer.analyzePrecedents(classFiles);
  }

  /**
   * Parses command line arguments
   * @param args - the arguments to parse
   * @return a CLInput that describes the given arguments
   */
  public static CLInput parseCL(String [] args) {
    Options options = new Options();
    
    Option help = new Option("h", "Lists command options for the Turtle interpreter");
    help.setLongOpt("help");
    help.setArgs(0);
    help.setRequired(false);
    
    Option start = new Option("s", "The legislation to start enforcing.");
    start.setLongOpt("start");
    start.setArgs(1);
    start.setRequired(true);
    
    Option precs = new Option("p", "The directory which contains precedents to account for (as .class files)");
    precs.setLongOpt("prec");
    precs.setArgs(1);
    precs.setRequired(false);
    
    options.addOption(help);
    options.addOption(start);
    options.addOption(precs);
    
    HelpFormatter usageFormatter = new HelpFormatter();
    CommandLineParser parser = new DefaultParser();
    
    try {
      CommandLine commandLine = parser.parse(options, args);
      if (commandLine.hasOption(help.getOpt()) || 
          commandLine.hasOption(help.getLongOpt())) {
        usageFormatter.printHelp("poli [-p DIRECTORY] -s LEGISLATION [LEGISLATION.bill ....]", options);
        //System.out.println("---RETURNING NULL HELP");
        return null;
      }
           
      String precDir = null;
      
      if (commandLine.hasOption(precs.getOpt()) || 
          commandLine.hasOption(precs.getLongOpt())) {
        precDir = commandLine.hasOption(precs.getOpt()) ? 
                    commandLine.getOptionValue(precs.getOpt()) : 
                    ( commandLine.hasOption(precs.getLongOpt()) ?
                      commandLine.getOptionValue(precs.getLongOpt()) : null );
      }
      
      String startLegis = null;
      
      if (commandLine.hasOption(start.getOpt()) || 
          commandLine.hasOption(start.getLongOpt())) {
        startLegis = commandLine.hasOption(start.getOpt()) ? 
                       commandLine.getOptionValue(start.getOpt()) : 
                       ( commandLine.hasOption(start.getLongOpt()) ?
                         commandLine.getOptionValue(start.getLongOpt()) : null );
      }
      
      String [] legislation = commandLine.getArgs();
      
      return new CLInput(startLegis, precDir, legislation);
    } catch (ParseException e) {
      usageFormatter.printHelp("poli [-p DIRECTORY] -s LEGISLATION [LEGISLATION ....]", options);
      return null;
    }
  }
}
