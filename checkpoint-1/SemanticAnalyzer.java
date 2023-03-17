import absyn.ArrayDec;
import absyn.AssignExp;
import absyn.CallExp;
import absyn.CompoundExp;
import absyn.Dec;
import absyn.DecList;
import absyn.Exp;
import absyn.ExpList;
import absyn.FunctionDec;
import absyn.IfExp;
import absyn.IndexVar;
import absyn.IntExp;
import absyn.NilExp;
import absyn.OpExp;
import absyn.ReadExp;
import absyn.RepeatExp;
import absyn.ReturnExp;
import absyn.SimpleDec;
import absyn.SimpleVar;
import absyn.Type;
import absyn.Var;
import absyn.VarDec;
import absyn.VarDecList;
import absyn.VarExp;
import absyn.WhileExp;
import absyn.WriteExp;
import symbol.Symbol;

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

    private boolean containsMain;
    public boolean containsErrors;

    public String displaySymbolTable() {
        // TODO: change to symbol table tostring method call
        return "";
    }

    // public void visit( ExpList exp){
    // //TODO: Implement visitor function
    // }

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

    public void visit(Var expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(VarDec expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(VarDecList expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(VarExp expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(WhileExp expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(CompoundExp expr, int level) {
        // TODO: Implement visitor function
    }

    public void visit(Type ty, int level) {
        // TODO: Implement visitor function
    }

    // Visit each expression in the linked list of expressions until the tail is
    // reached
    public void visit(ExpList exp, int level) {
        while (exp != null) {
            if (exp.head != null) {
                visit(exp.head, level);
                exp = exp.tail;
            }
        }
    }

    public void visit(ArrayDec exp, int level) {

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
        // Check if the variable is declared before assignment
        Symbol symbol = table.lookupSymbol(exp.lhs.var.);
        if (symbol == null) {
            containsErrors = true;
            // Error: variable not found
        } else {
            // Check if the type of expression matches the type of the variable
            int expType = visit(exp.expression);
            if (expType != symbol.type) {
                containsErrors = true;
                // Error: type mismatch
            }
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
        visit(exp.left);
        visit(exp.right);

        // Check the types of the operands and the compatibility with the operator
        int leftType = visit(exp.left);
        int rightType = visit(exp.right);

        if (leftType != rightType || leftType != Type.INT) {
            containsErrors = true;
            // Error: type mismatch or invalid type for operator
        }
    }

    public void visit(ReadExp exp) {
        // Check if the variable is declared before reading
        Symbol symbol = SymbolTable.lookupSymbol(exp.variable);
        if (symbol == null) {
            containsErrors = true;
            // Error: variable not found
        }
    }

    public void visit(RepeatExp exp) {
        visit(exp.test);
        visit(exp.body);
    }

    public void visit(WriteExp exp) {
        visit(exp.exp);
    }

}