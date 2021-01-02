package jg.ps.parser.nodes;

/**
 * Describes the retrieval of an instance of a definition's member value, as
 * designated by it's name.
 * @author Jose
 */
public class InstanceDeref extends Expr{

  private final Expr target;
  private final String memberName;
  
  public InstanceDeref(int line, int column, Expr target, String memberName) {
    super(line, column);
    this.target = target;
    this.memberName = memberName;
  }

  public Expr getTarget() {
    return target;
  }
  
  public String getMemberName() {
    return memberName;
  }
  
  @Override
  public String toString() {
    return "DEREF ~ target:"+target+" : "+memberName;
  }
}
