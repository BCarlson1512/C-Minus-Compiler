import java.util.ArrayList;
import java.util.HashMap;

import absyn.*;
import symbol.*;

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
    public void addSymbolToScope(String id, Symbol sym) {
        HashMap<String, Symbol> scope = symTable.get(symTable.size() - 1);
        scope.put(id, sym);
        // may cause issues revisit this
        symTable.set(symTable.size() - 1, scope);
    }

    // removes current scope, displays when necessary and deletes from stack
    public void deleteScope() {
        int currLevel = symTable.size();
        if (currLevel > 0) {
            if (outputSymbolTable) {
                indent(currLevel - 1);
                System.out.println("Scope level: " + currLevel);
                displayScope(currLevel);
            }
            symTable.remove(currLevel - 1);
        }
    }

    // Get symbol from scope on top of stack
    public Symbol lookupSymbol(String name) {
        return symTable.get(symTable.size() - 1).get(name);
    }

    // Get base level functions
    public Symbol lookupFn(String sym) {
        // TODO: build getter for functions within scope
        Symbol res = null;
        try {
            res = symTable.get(0).get(sym);
        } catch (Exception e) {
            // in the event we fetch from an empty hashmap or symbol does not exist
            // do nothing
        }
        return res;
    }

    public int countFnParams(String sym) { // counts function parameters
        FunctionSymbol fn = (FunctionSymbol) lookupFn(sym);
        return fn != null ? fn.params.size() : 0;
    }

    // checks if symbols are in the same scope
    public boolean compareScopes(String sym) {
        int tableSize = symTable.size() - 1;
        return getScope(tableSize).containsKey(sym);
    }

    // TODO: test
    public void displayScope(int level) {
        int numScopes = symTable.size();
        for (String s : getScope(level).keySet()) {
            Symbol sym = getScope(level).get(s);
            if (sym instanceof FunctionSymbol) {
                printFunctionSym((FunctionSymbol) sym, level, s);
            } else if (sym instanceof ArraySymbol) {
                printArraySym((ArraySymbol) sym, level, s);
            } else if (sym instanceof VariableSymbol) {
                printVarSym((VariableSymbol) sym, level, s);
            }
        }
    }

    private void printArraySym(ArraySymbol sym, int level, String key) { // for all array symbols
        indent(level);
        System.out.println("Array: " + getType(sym.type) + " " + key + "[" + sym.array_size + "]");
    }

    private void printVarSym(VariableSymbol sym, int level, String key) { // for all varsymbols
        indent(level);
        System.out.println("Var: " + getType(sym.type) + " " + key);
    }

    private void printFunctionSym(FunctionSymbol fn, int level, String key) { // for all function symbols
        indent(level);
        System.out.println("Function: " + getType(sym.type) + " " + key + " (");
        // print all params
        for (Symbol param : sym.params) {
            if (param instanceof ArraySymbol) {
                System.out.print(getType(param.type) + "[]");
            } else if (param instanceof VarSymbol) {
                System.out.print(getType(param.type));
            }
            System.out.print(",");
        }
        System.out.println(")");
    }

    private HashMap<String, Symbol> getScope(int scopeId) {
        return symTable.get(scopeId);
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
