import absyn.*;
import symbol.*;

public class SemanticAnalyzer {
    // TODO: Validate Array types (array range is int, index is int)
    // TODO: Validate assignments
    // TODO: Validate operations
    // TODO: Validate test conditions (must be int)

    private SymbolTable table;
    private int fnReturnType;
    private boolean shouldDisplaySymbols = false;

    private boolean containsMain = false;
    public boolean containsErrors = false;

    public String displaySymbolTable() {
        // TODO: change to symbol table tostring method call
        return "";
    }

    /**
     * This constructor ends up being called by CUP
     */
    public SemanticAnalyzer(boolean shouldDisplaySymbols, DecList result) {
        table = new SymbolTable(shouldDisplaySymbols);

        visit(result);
    }

    public void visit(DecList decList) {
        while (decList != null) {
            if (decList.head == null) {
                visit(decList.head);
            }
            decList = decList.tail;
        }
    }

    public void visit(CallExp exp) {
        String fnName = exp.func;
        int row = exp.row + 1;
        FunctionSymbol fnSym = (FunctionSymbol) table.lookupFn(fnName);
        int numFnParams = table.countFnParams(name);
        int numCallParams = exp.count_params();

        if (table.lookupFn(fnName) == null) { // check existence of function
            updateContainsErrors();
            System.err.println("Error: Undefined function '" + fnName + "' on line: " + row);
        }

        if (numFnParams != numCallParams) { // check for valid number of parameters
            updateContainsErrors();
            System.err.println("Error: Invalid number of parameters for function '" + fnName + "' on line: " + row);
        }

        validateFunctionCall(exp.args, fnSym, numFnParams); // validate individual params
    }

    public void visit(DecList decList, int level) {
        table.newScope();

        // validate input and output functions, kinda hackish to make things work
        FunctionSymbol inputSym = new FunctionSymbol(Type.INT, "input", new ArrayList<Symbol>())
        table.addSymbolToScope("input", inputSym);

        // store empty int parameter for vars, kinda hackish
        ArrayList<Symbol> params = new ArrayList<Symbol>();
        params.add(new VarSymbol(Type.INT, ""));

        FunctionSymbol outputSym = new FunctionSymbol(Type.VOID, "output", params);
        table.addSymbolToScope("output", outputSym);

        if (!this.hasMain) { // check for main function
            updateContainsErrors();
            System.err.println("Error: Missing Main function");
        }

        table.deleteScope();
    }

    public void visit(Dec declaration) {
        if (declaration instanceof VarDec) {
            visit((VarDec) declaration);
        } else if (declaration instanceof FunctionDec) {
            visit((FunctionDec) declaration);
        }
    }

    public void visit(Exp expr, int level) {
        if (expr instanceof ReturnExp) {
            visit((ReturnExp) expr);
        } else if (expr instanceof CompoundExp) {
            visit((CompoundExp) expr);
        } else if (expr instanceof WhileExp) {
            visit((WhileExp) expr);
        } else if (expr instanceof IfExp) {
            visit((IfExp) expr);
        } else if (expr instanceof AssignExp) {
            visit((AssignExp) expr);
        } else if (expr instanceof OpExp) {
            visit((OpExp) expr);
        } else if (expr instanceof CallExp) {
            visit((CallExp) expr);
        } else if (expr instanceof VarExp) {
            visit((VarExp) expr);
        }
    }

    public void visit(FunctionDec dec) {
        // TODO: Implement visitor function
    }

    public void visit(VarDec dec) {
        if (expr instanceof SimpleDec) {
            visit((SimpleDec) expr);
        } else if (expr instanceof ArrayDec) {
            visit((ArrayDec) expr);
        }
    }

    public void visit(Var expr) {
        if (expr instanceof indexVar) {
            visit((IndexVar) expr);
        } else if (expr instanceof SimpleVar) {
            visit((SimpleVar) expr);
        }
    }

    // TODO: This kinda looks scary, maybe sanity check/refactor if needed
    public void visit(SimpleVar expr) {
        String varName = expr.name;
        int row = expr.row + 1;

        if (table.get(varName) != null) {
            if (table.get(varName) instanceof VarSymbol) { // variable declaration
                // check for type mismatches
                if (table.lookupSymbol(varName).type != Type.INT) {
                    updateContainsErrors();
                    System.err.println("Error: Expected integer instead of " + getType(table.lookupSymbol(varName).type)
                            + "variable '" + varName + "' on line: " + row);
                } else if (table.lookupSymbol(name).type != Type.VOID) {
                    updateContainsErrors();
                    System.err.println("Error: Expected void instead of " + getType(table.lookupSymbol(varName).type)
                            + "variable '" + varName + "' on line: " + row);
                } else if (table.lookupSymbol(name).type != Type.BOOL) {
                    updateContainsErrors();
                    System.err.println("Error: Expected boolean instead of " + getType(table.lookupSymbol(varName).type)
                            + "variable '" + varName + "' on line: " + row);
                }
            } else if (table.lookupSymbol(varName).type != Type.INT || table.lookupSymbol(varName).type != Type.BOOL
                    || table.lookupSymbol(varName).type != Type.VOID) { // array declaration
                updateContainsErrors();
                System.err
                        .println("Error: Invalid conversion of array '" + varName + "' to static type on line: " + row);
            }
        } else { // var is not defined
            updateContainsErrors();
            System.err.println("Error: Undefined variable '" + varName + "' on line: " + row);
        }
    }

    // TODO: may need tweaks
    public void visit(IndexVar expr) {
        Symbol sym = table.lookupSymbol(expr.name);
        int row = expr.row + 1;
        if (sym != null && !(sym instanceof ArraySymbol)) {
            updateContainsErrors();
            System.err.println("Error: Symbol '" + expr.name + "' is not an array line: " + row);
        }
        visit(expr.index);
    }

    public void visit(ReturnExp expr) {
        // Validate void functions
        if (fnReturnType == Type.VOID) {
            if (expr.test != null) {
                updateContainsErrors();
                int row = expr.row + 1;
                System.err.println("Error: Void type function expects no return value, line: " + row);
                return;
            }
        } else {
            // Check that function has a return value
            if (expr.test == null) {
                updateContainsErrors();
                int row = expr.row + 1;
                System.err.println("Error: Non-Void type expects return value, line: " + row);
            } else { // Check the return value matches
                visit(expr.test);
            }
        }
    }

    public void visit(VarDecList expr) {
        while (expr != null) {
            if (expr.head == null) {
                visit(expr.head);
            }
            expr = expr.tail;
        }
    }

    public void visit(VarExp expr) {
        visit(expr.var);
    }

    public void visit(WhileExp expr) {
        visit(expr.test);
        visit(expr.body);
    }

    public void visit(CompoundExp expr) {
        // TODO: Implement visitor function
    }

    public void visit(ArrayDec exp) {

        // Arrays cannot be void
        if (exp.type.type == Type.VOID) {
            System.err.println("[Line " + exp.row + "] Error: Array type" + exp.name + " cannot be void");
        }

        // Check if we're redeclaring a variable
        if (table.lookupSymbol(exp.name) != null) {
            System.err.println("[Line " + exp.row + "] Error: Variable " + exp.name + " already declared");
        }
    }

    public void visit(AssignExp exp) {
        // Visit variable and expression
        // visit(exp.lhs.var);
        // visit left hand and right hand sides?
        // visit(exp.lhs); //TODO: uncomment
        visit(exp.rhs);

        // Check if the variable is declared before assignment
        // Symbol varSymbol = symbolTable.lookupSymbol(exp.lhs.var);
        // if (varSymbol == null) {
        // Report undeclared variable error
        // reportUndeclaredVariableError(exp.row, exp.col, exp.var.name);
        // } else {
        // Check if the types of the left-hand side and right-hand side expressions are
        // compatible
        // Assuming that the 'type' field in the Symbol class represents the type of the
        // variable
        // if (compatibleTypes(varSymbol.type, exp.exp)) {
        // The types are compatible, continue with the analysis
        // } else {
        // Report a type error since the types are not compatible
        // reportTypeError(exp.row, exp.col);
        // }
        // }
    }

    private boolean compatibleTypes(int lhsType, Exp rhsExp) {
        // Assuming that IntExp represents integer expressions
        if (lhsType == Type.INT && rhsExp instanceof IntExp) {
            return true;
        }

        // TODO: Add compatibility checks for other types if necessary

        // The types are not compatible
        return false;
    }

    // Visit each expression in the linked list of expressions until the tail is
    // reached
    public void visit(ExpList exp) {
        while (exp != null) {
            visit(exp.head);
            exp = exp.tail;
        }
    }

    // Generic visit method for expressions
    // We type cast the expression to the correct type and call the appropriate
    // visitor method
    public void visit(Exp exp) {
        if (exp instanceof ReturnExp) {
            visit((ReturnExp) exp);
        } else if (exp instanceof CompoundExp) {
            visit((CompoundExp) exp);
        } else if (exp instanceof WhileExp) {
            visit((WhileExp) exp);
        } else if (exp instanceof IfExp) {
            visit((IfExp) exp);
        } else if (exp instanceof AssignExp) {
            visit((AssignExp) exp);
        } else if (exp instanceof OpExp) {
            visit((OpExp) exp);
        } else if (exp instanceof CallExp) {
            visit((CallExp) exp);
        } else if (exp instanceof VarExp) {
            visit((VarExp) exp);
        }
    }

    public void visit(IfExp exp) {
        visit(exp.test);
        visit(exp.thenpart);
        if (exp.elsepart != null) {
            visit(exp.elsepart);
        }
    }

    public void visit(OpExp exp) {
        // Visit left and right operands
        visit(exp.left);
        visit(exp.right);

        // Check if the operator is a relational, logical, or arithmetic operator
        if (isRelationalOperator(exp.op) || isLogicalOperator(exp.op)) {
            // Handle boolean expression
            if (!(exp.left instanceof IntExp) || !(exp.right instanceof IntExp)) {
                // Report a type error since both operands should be integers for relational and
                // logical operators
                reportTypeError(exp.row, exp.col);
            } else {
            }
        } else if (isArithmeticOperator(exp.op)) {
            // Handle arithmetic operators
            if (!(exp.left instanceof IntExp) || (exp.right instanceof IntExp)) {
                // Report a type error since both operands should be integers for arithmetic
                // operators
                reportTypeError(exp.row, exp.col);
            }
        } else {
            // Handle other types of operators (unary operators, etc.)
            // Check if the types are correct for the specific operator
        }
    }

    private boolean isRelationalOperator(int op) {
        return op == OpExp.LT || op == OpExp.LTE || op == OpExp.GT || op == OpExp.GTE || op == OpExp.EQ
                || op == OpExp.NOTEQ;
    }

    private boolean isLogicalOperator(int op) {
        return op == OpExp.AND || op == OpExp.OR;
    }

    private boolean isArithmeticOperator(int op) {
        return op == OpExp.PLUS || op == OpExp.MINUS || op == OpExp.TIMES || op == OpExp.OVER || op == OpExp.DIVIDE;
    }

    public void visit(ReadExp exp) {
    }

    public void visit(RepeatExp exp) {
        visit(exp.test);
        visit(exp.exps);
    }

    public void visit(WriteExp exp) {
        visit(exp);
    }

    private void reportTypeError(int row, int col) {
        System.err.println("Type error at row " + row + ", col " + col);
        updateContainsErrors();
    }

    // for tracking errors
    private void updateContainsErrors() {
        this.containsErrors = true;
    }

    // iterate over list of function parameters, validating them one by one
    private void validateFunctionCall(ExpList params, FunctionSymbol fnSym, int numFnParams) {
        for (int i = 0; i < numFnParams; i++) {
            Exp currParam = params.head;
            Symbol sym = FunctionSymbol.params.get(i);
            if (symbol instanceof VarSymbol)
                visit(param);
            params = params.tail;
        }
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