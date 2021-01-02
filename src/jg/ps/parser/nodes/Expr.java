package jg.ps.parser.nodes;

/**
 * Represents a grammatically valid Seahorse expression - arithmetic, structural, etc.
 * 
 * An Expr doesn't necessarily represent a valid Seahorse expression.
 * For example, expressions that refer to a undefined variable aren't valid, but 
 * are grammatically valid.
 * 
 * @author Jose
 *
 */
public abstract class Expr {

  private final int line;
  private final int column;
    
  public Expr(int line, int column) {
    this.line = line;
    this.column = column;
  }
  
  /*
  public void setType(GenType type){
    this.attachedType = type;
  }
  
  public GenType getType() {
    return attachedType;
  }
  */
  
  public int getLineNumber() {
    return line;
  }
  
  public int getColumnNumber() {
    return column;
  }
  
  public abstract String toString();
}
