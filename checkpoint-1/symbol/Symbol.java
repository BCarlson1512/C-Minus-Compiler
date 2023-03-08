package symbol;
/**
*
* Base class for all symbol objects
* @param name (String) the name of the symbol
* @param type (int) the type of the symbol, stored as a numeric constant
* @param offset (int) the column offset
*
*/
public abstract class Symbol {
    public String name;
    public int type;
    public int offset;
}