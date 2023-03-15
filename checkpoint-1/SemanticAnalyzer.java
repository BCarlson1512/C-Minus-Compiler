import java.util.ArrayList;
import java.util.HashMap;

public class SemanticAnalyzer {
    // TODO: implement symbol table class
    // TODO: Validate Array types (array range is int, index is int)
    // TODO: Validate assignments
    // TODO: Validate operations
    // TODO: Function call validation
    // TODO: Validate test conditions (must be int)

    private HashMap<String, ArrayList<NodeType>> table;

    //TODO: implement symbol table class before using
    private SymbolTable SymbolTable;
    private int fnReturnType;

    private boolean containsMain;
    public boolean containsErrors;

    public String displaySymbolTable(){
        // TODO: change to symbol table tostring method call
        return ""
    }

    public void visit( ExpList exp){
        //TODO: Implement visitor function
    }

    public void visit( ArrayDec expList, int level){
        //TODO: Implement visitor function
    }

    public void visit( AssignExp exp){
        //TODO: Implement visitor function
    }

    public void visit( IfExp exp){
        //TODO: Implement visitor function
    }

    public void visit( IntExp exp){
        //TODO: Implement visitor function
    }

    public void visit( OpExp exp){
        //TODO: Implement visitor function
    }

    public void visit( ReadExp exp){
        //TODO: Implement visitor function
    }

    public void visit( RepeatExp exp){
        //TODO: Implement visitor function
    }

    public void visit( WriteExp exp){
        //TODO: Implement visitor function
    }

    public void visit( CallExp exp, int level){
        //TODO: Implement visitor function
    }

    public void visit( DecList decList, int level){
        //TODO: Implement visitor function
    }

    public void visit( Dec declaration, int level){
        //TODO: Implement visitor function
    }

    public void visit( Exp expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( FunctionDec expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( NilExp expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( ReturnExp expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( SimpleDec expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( SimpleVar expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( IndexVar expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( Var expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( VarDec expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( VarDecList expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( VarExp expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( WhileExp expr, int level){
        //TODO: Implement visitor function
    }

    public void visit( CompoundExp expr, int level){
        //TODO: Implement visitor function
    }

    public void visit ( Type ty, int level){
        //TODO: Implement visitor function
    }
}