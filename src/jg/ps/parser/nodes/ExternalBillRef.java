package jg.ps.parser.nodes;

/**
 * For section invocations and definition instanciations, an
 * external Bill referral explicitly designates the host Bill
 * in which the respective section/definition is in.
 * @author Jose
 */
public class ExternalBillRef extends Expr{

  private final String billName;
  
  public ExternalBillRef(int line, int column, String billName) {
    super(line, column);
    this.billName = billName;
  }
  
  public String getBillName() {
    return billName;
  }
  
  @Override
  public String toString() {
    return "REGARDS ~ '"+billName+"'";
  }
}
