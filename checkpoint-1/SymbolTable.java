import java.util.ArrayList;
import java.util.HashMap;

import absyn.Type;
import symbol.Symbol;

public class SymbolTable {

    private ArrayList<HashMap<String, Symbol>> symTable;
    private boolean outputSymbolTable;

    final static int SPACES = 4;

    public SymbolTable(boolean outputSymbolTable) {
        this.outputSymbolTable = outputSymbolTable;
        symTable = new ArrayList<HashMap<String, Symbol>>();
    }

    private void indent(int level) { // TODO: may be tweaked for toString() purposes
        for (int i = 0; i < level * SPACES; i++)
            System.out.print(" ");
    }

    public void createNewScope() {
        symTable.add(new HashMap<String, Symbol>());
    }

    // Adds symbol to the current scope (top of stack)
    public void addSymbolToScope(Symbol sym) {
        HashMap<String, Symbol> scope = symTable.get(symTable.size() - 1);
        scope.put(sym.name, sym);
        symTable.set(symTable.size() - 1, scope);
    }

    public void deleteScope() {
        // TODO: remove scopes from list
    }

    // Get symbol from scope on top of stack
    public Symbol lookupSymbol(String name) {
        return symTable.get(symTable.size() - 1).get(name);
    }

    public void lookupFn() {
        // TODO: build getter for functions within scope
    }

    public void displayScope(int level) {
        // TODO: display scopes
    }

    // checks if symbols are in the same scope
    public boolean compareScopes(String sym) {
        return false;
    }

    // private HashMap<String, Symbol> getScope(int scopeId) {
    // return SymbolTable.get(scopeId);
    // }

    public void display() {
        // TODO: Display all symbol tables in a nice format
        System.out.println("PRINT SYMBOL TABLE");
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
