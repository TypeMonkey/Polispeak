package jg.ps.parser.nodes.atoms;

/**
 * Describes the name of a definition.
 * 
 * A definition's "simple" name is the name of its definition.
 * 
 * A definition's "full" name is the name of it's host legislation 
 * and name of its definition, formatted as: legislation_name$definition_name.
 * 
 * Two types are equal if their full name is the same.
 * 
 * @author Jose
 */
public class Type extends Atom<String>{

  public static final Type INT_TYPE = new Type(-1, -1, "Integer", "The Constitution");
  public static final Type FLOAT_TYPE = new Type(-1, -1, "Float", "The Constitution");
  public static final Type STR_TYPE = new Type(-1, -1, "String", "The Constitution");
  public static final Type UNKNOWN_TYPE = new Type(-1, -1, "Unknown", "The Constitution");
  public static final Type NULL_TYPE = new Type(-1, -1, "Null", "The Constitution");
  public static final Type VOID_TYPE = new Type(-1, -1, "Void", "The Constitution");
  
  private final String hostBill;
  private final String typeName;
  
  /**
   * Constructs a Type
   * @param line - the line number at which this type is being referred
   * @param column - the column number at which this type is being referred
   * @param typeName - the simple name of the definition this type is referring to
   * @param hostBill - the legislation the definition is in
   */
  public Type(int line, int column, String typeName, String hostBill) {
    super(hostBill+"$"+typeName, line, column);
    this.hostBill = hostBill;
    this.typeName = typeName;
  }
  
  public boolean equals(Object object) {
    if (object instanceof Type) {
      Type other = (Type) object;
      return other.hostBill.equals(hostBill) && other.typeName.equals(typeName);
    }
    return false;
  }
  
  public int hashCode() {
    return getFullName().hashCode();
  }
  
  public String getTypeName() {
    return typeName;
  }
  
  public String getHostBill() {
    return hostBill;
  }
  
  public String getFullName() {
    return getActualValue();
  }
  
  @Override
  public String toString() {
    return "TYPE ~ "+actualValue;
  }
}
