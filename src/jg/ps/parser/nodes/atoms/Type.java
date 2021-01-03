package jg.ps.parser.nodes.atoms;

public class Type extends Atom<String>{

  public static final Type INT_TYPE = new Type(-1, -1, "Integer", "The Constitution");
  public static final Type FLOAT_TYPE = new Type(-1, -1, "Float", "The Constitution");
  public static final Type STR_TYPE = new Type(-1, -1, "String", "The Constitution");
  public static final Type UNKNOWN_TYPE = new Type(-1, -1, "Unknown", "The Constitution");
  public static final Type NULL_TYPE = new Type(-1, -1, "Null", "The Constitution");
  public static final Type VOID_TYPE = new Type(-1, -1, "Void", "The Constitution");
  
  private final String hostBill;
  private final String typeName;
  
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
    return toString().hashCode();
  }
  
  public String getTypeName() {
    return typeName;
  }
  
  public String getHostBill() {
    return hostBill;
  }
  
  @Override
  public String toString() {
    return "TYPE ~ "+actualValue;
  }
}
