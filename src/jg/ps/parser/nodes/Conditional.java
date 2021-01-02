package jg.ps.parser.nodes;

/**
 * Describe the enforcement of a certain part of a Bill given
 * that a certain clause is true. It can also describe the enforcement
 * of certain part of a Bill given a certain clause is false.
 * @author Jose
 */
public class Conditional extends Expr{

  private final Expr clause;
  private final Expr trueRoute;
  private final Expr falseRoute;
  
  public Conditional(int line, int column, Expr clause, Expr trueRoute) {
    this(line, column, clause, trueRoute, null);
  }
  
  public Conditional(int line, int column, Expr clause, Expr trueRoute, Expr falseRoute) {
    super(line, column);
    this.clause = clause;
    this.trueRoute = trueRoute;
    this.falseRoute = falseRoute;
  }
  
  public Expr getClause() {
    return clause;
  }
  
  public Expr getTrueRoute() {
    return trueRoute;
  }
  
  public Expr getFalseRoute() {
    return falseRoute;
  }

  @Override
  public String toString() {
    return "COND ~ CLAUSE="+clause+" ? "+trueRoute+" : "+falseRoute;
  }
}
