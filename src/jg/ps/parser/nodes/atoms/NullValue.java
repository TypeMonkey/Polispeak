package jg.ps.parser.nodes.atoms;

/**
 * Describes the null value.
 * 
 * What's the difference between null and void?
 * 
 * -> Say we have a can of soup. We know this for sure because
 *    this can says "Soup" on it.
 * -> If we open the can and we see it's completely empty, then
 *    say our can of soup is null. There's no soup in it!
 * -> A void soup is a bit more difficult to explain. It means that
 *    the there was never supposed to see/expect a can of soup in the first place.
 * 
 * The null value can only be assigned to types that are
 * referring to definitions.
 * 
 * @author Jose
 *
 */
public class NullValue extends Atom<String>{

  /**
   * Constructs a NullValue
   * @param line - the line number at which the null value was used at
   * @param column - the column number at which the null value was used at
   */
  public NullValue(int line, int column) {
    super("null", line, column);
  }

  @Override
  public String toString() {
    return "NULL_VAL";
  }

}
