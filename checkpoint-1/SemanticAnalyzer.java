import absyn.*;
import symbol.*;

public class SemanticAnalyzer {
    // TODO: implement symbol table class
    // TODO: Validate Array types (array range is int, index is int)
    // TODO: Validate assignments
    // TODO: Validate operations
    // TODO: Function call validation
    // TODO: Validate test conditions (must be int)

    // TODO: implement symbol table class before using
    private SymbolTable table;
    private int fnReturnType;

    private boolean containsMain = false;
    public boolean containsErrors = false;

    public String displaySymbolTable() {
        // TODO: change to symbol table tostring method call
        return "";
    }

    // public void visit( ArrayDec expList, int level){
    // //TODO: Implement visitor function
    // }

    // public void visit( AssignExp exp){
    // //TODO: Implement visitor function
    // }

    // public void visit( IfExp exp){
    // //TODO: Implement visitor function
    // }

    // public void visit( IntExp exp){
    // //TODO: Implement visitor function
    // }

    // public void visit( OpExp exp){
    // //TODO: Implement visitor function
    // }

    // public void visit( ReadExp exp){
    // //TODO: Implement visitor function
    // }

    // public void visit( RepeatExp exp){
    // //TODO: Implement visitor function
    // }

    // public void visit( WriteExp exp){
    // //TODO: Implement visitor function
    // }

    public void visit(CallExp exp, int level) {
        // TODO: Implement visitor function
    }

    public void visit(DecList decList, int level) {
        // TODO: Implement visitor function
    }

    public void visit(Dec declaration, int level) {
        // TODO: Implement visitor function
    }

    public void visit(Exp expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(FunctionDec expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(NilExp expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(ReturnExp expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(SimpleDec expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(SimpleVar expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(IndexVar expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(Var expr) {
        // TODO: Implement visitor function
    }

    public void visit(VarDec expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(VarDecList expr) {
        // TODO: Implement visitor function
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

    public void visit(Type ty) {
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

    public void visit(IntExp exp) {
        // No semantic analysis required for integer literals
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
        containsErrors = true;
    }

}