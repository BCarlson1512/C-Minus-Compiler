package symbol;

/**
* VariableSymbol Class
* Stores all inherited symbols from superclass
*/
public class VariableSymbol extends Symbol {
    public VariableSymbol(String name, int type, int offset) {
        this.name = name;
        this.type = type;
        this.offset = offset;
    }
    public VariableSymbol(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
