package symbol;
import java.util.ArrayList;
/**
* Function symbol class
* Stores all symbol params + a list of parameters
* Stores an address to function
*/
public class FunctionSymbol extends Symbol {
    public int fun_address;
    public ArrayList<Symbol> params;

    public FunctionSymbol(String name, int type, int offset, int address) {
        this.params = new ArrayList<Symbol>();
        this.type = type;
        this.name = name;
        this.offset = offset;
        this.address = address;
    }
    public FunctionSymbol(int type, String name, int offset, ArrayList<Symbol> params, int address) {
        this.params = params;
        this.type = type;
        this.name = name;
        this.offset = offset;
        this.address = address;
    }
}
