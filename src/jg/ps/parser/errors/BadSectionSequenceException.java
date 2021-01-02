package jg.ps.parser.errors;

import jg.ps.common.LegislativeException;

public class BadSectionSequenceException extends LegislativeException{

  public BadSectionSequenceException(int actualNumber, int expectedNumber, int line, int column, String billName) {
    super("Section "+actualNumber+" must be numbered with "+expectedNumber+"."+System.lineSeparator()+
          "Sections must be numbered in increasing order , starting at 1, from top to bottom.", line, column, billName);
  }

}
