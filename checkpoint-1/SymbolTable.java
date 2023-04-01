import java.util.ArrayList;
import java.util.HashMap;

import absyn.*;
import symbol.*;

public class SymbolTable {

    private ArrayList<HashMap<String, Symbol>> symTable;
    private boolean outputSymbolTable;
    private String displayString = "";

    final static int SPACES = 4;

    public SymbolTable(boolean outputSymbolTable) {
        this.outputSymbolTable = outputSymbolTable;
        symTable = new ArrayList<HashMap<String, Symbol>>();
    }

    public String toString() {
        return this.displayString;
    }

    private String indent(int level) {
        String res = "";
        for (int i = 0; i < level * SPACES; i++)
            res += " ";
        return res;
    }

    public void createNewScope() {
        symTable.add(new HashMap<String, Symbol>());
    }

    // Adds symbol to the current scope (top of stack)
    public void addSymbolToScope(String id, Symbol sym) {
        HashMap<String, Symbol> scope = symTable.get(symTable.size() - 1);
        scope.put(id, sym);
        // may cause issues revisit this
        // symTable.set(symTable.size() - 1, scope);
    }

    // removes current scope, displays when necessary and deletes from stack
    public void deleteScope() {
        int currLevel = symTable.size();
        if (currLevel > 0) {
            if (outputSymbolTable) {
                displayString = indent(currLevel) + displayString;
                displayString = "Scope level: " + currLevel + "\n" + displayString;
                displayScope(currLevel - 1);
                displayString += "Leaving Scope Level: " + currLevel + "\n";
            }
            symTable.remove(currLevel - 1);
        }
    }

    // Get symbol from scope on top of stack
    public Symbol lookupSymbol(String name) {
        for (HashMap<String, Symbol> scope : symTable) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    // Get base level functions
    public Symbol lookupFn(String sym) {
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

    public void displayScope(int level) {

        int numScopes = symTable.size();
        for (String s : getScope(numScopes - 1).keySet()) {
            Symbol sym = getScope(numScopes - 1).get(s);
            if (sym instanceof FunctionSymbol) {
                printFunctionSym((FunctionSymbol) sym, level, s);
            } else if (sym instanceof ArraySymbol) {
                printArraySym((ArraySymbol) sym, level, s);
            } else if (sym instanceof VariableSymbol) {
                printVarSym((VariableSymbol) sym, level, s);
            }
        }
        //displayString = "Leaving Scope Level: " +getScopeType(level) +"\n"+ displayString;
    }

    private void printArraySym(ArraySymbol sym, int level, String key) { // for all array symbols
        displayString = indent(level) + displayString;
        displayString = "Array: " + getType(sym.type) + " " + key + "[" + sym.array_size + "]" + "\n" + displayString;
    }

    private void printVarSym(VariableSymbol sym, int level, String key) { // for all varsymbols
        displayString = indent(level) + displayString;
        displayString = "Var: " + getType(sym.type) + " " + key + "\n" + displayString;
    }

    private void printFunctionSym(FunctionSymbol fn, int level, String key) { // for all function symbols
        String functionString = "";
        functionString += indent(level);
        functionString += "Function: " + getType(fn.type) + " " + key + " (\n";
        // print all params
        for (Symbol param : fn.params) {
            if (param instanceof ArraySymbol) {
            } else if (param instanceof VariableSymbol) {
                functionString += getType(param.type);
            }
            functionString += ",";
        }
        functionString += ")\n";
        displayString += functionString;
    }

    private void printVarSymbol(VariableSymbol sym, int level, String key) {
        displayString = indent(level) + displayString;
        displayString = "Var: " + getType(sym.type) + " " + key + "\n" + displayString;
    }

    private HashMap<String, Symbol> getScope(int scopeId) {
        return symTable.get(scopeId);
    }

    // needs to be publicly scoped for asm generator
    // check if symbols exist, returns scope the symbol exists in, otherwise -1
    public int exists(String sym) {
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
