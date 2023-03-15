import java.util.ArrayList;
import java.util.HashMap;

import absyn.Type;
import symbol.Symbol;

import absyn.*;
import symbol.*;

public class SymbolTable {

    private ArrayList<HashMap<String, Symbol>> symTable;
    private boolean displaySymbols;

    final static int spaces = 4;

    public SymbolTable(boolean displaySymbols) {
        this.displaySymbols = displaySymbols;
        symTable = new ArrayList<HashMap<String, Symbol>>();
    }

    private void indent( int level ) { //TODO: may be tweaked for toString() purposes
        for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
    }

    public void createNewScope() {
        symTable.add(new HashMap<String, Symbol>());
    }

    public void addSymbolToScope() {
        //TODO: add symbols to scope
    }

    public void deleteScope() {
        //TODO: remove scopes from list
    }

    public void lookupSymbol() {
        //TODO: build a getter for symbols within scope
    }

    public void lookupFn() {
        //TODO: build getter for functions within scope
    }

    public void displayScope(int level) {
        //TODO: display scopes
    }

    // checks if symbols are in the same scope
    public boolean compareScopes(String sym) {
        return getScope()
    }

    private HashMap<String, Symbol> getScope(int scopeId) {
        return SymbolTable.get(scopeId);
    }

    // check if symbols exist, returns scope the symbol exists in, otherwise -1
    private int exists(String sym) {
        for (HashMap<String, Symbol> scope : symTable) {
            if (scope.containsKey(sym)) {
                return symTable.indexOf(scope);
            }
        }
        return -1;
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
