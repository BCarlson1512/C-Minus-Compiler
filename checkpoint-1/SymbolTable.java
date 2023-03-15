import java.util.ArrayList;
import java.util.HashMap;

import absyn.Type;
import symbol.Symbol;

public class SymbolTable {
    // TODO: Implement absyn and symbol packages
    // TODO: Create hashmaps for each type of symbol
    // TODO: Testing for simple vars
    // TODO: Testing for array variables
    // TODO: Testing for functions/blocks
    // TODO: Undefined and Redefined errors testing

    private ArrayList<HashMap<String, Symbol>> symbolTable;
    private boolean SHOULD_OUTPUT_SYMBOL_TABLE;

    SymbolTable(boolean output) {
        symbolTable = new ArrayList<HashMap<String, Symbol>>();
        this.SHOULD_OUTPUT_SYMBOL_TABLE = output;
    }

    // Gets the string version of a type from the Type class
    private String getType(int type) {
        switch (type) {
            case Type.INT:
                return "INT";
            case Type.BOOL:
                return "BOOL";
            case Type.VOID:
                return "VOID";
            default:
                return "UNKNOWN";
        }
    }

}