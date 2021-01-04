package jg.ps.parser.nodes;

/**
 * Represents a grammatically valid Polispeak expression.
 * @author Jose
 */
public abstract class Expr {

  private final int line;
  private final int column;
    
  public Expr(int line, int column) {
    this.line = line;
    this.column = column;
  }
  
  public int getLineNumber() {
    return line;
  }
  
  public int getColumnNumber() {
    return column;
  }
  
  public abstract String toString();
}
