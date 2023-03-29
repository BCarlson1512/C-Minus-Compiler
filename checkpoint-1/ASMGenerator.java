import absyn.*;
import symbol.*;
import java.io.*;
import java.util.ArrayList;


public class ASMGenerator {

    public String fileName;

    private SymbolTable symTable;
    private DecList program;

    public ASMGenerator(DecList program, String filename) {
        this.program = program;
        this.fileName = filename;
        symTable = new SymbolTable(false);
    }

    // driver function
    public void generate() {

    }

    // wrapper for all other codegen functions
    public void generateASM(DecList tree) {

    }


    // VarDecList

    public void asmGen(VarDecList tree, int offset, boolean isParam) {

    }

    // Expression List

    public void asmGen(ExpList tree, int offset){

    }

    // General declarations

    public void asmGen(Dec tree){

    }

    // Var and param declarations

    public void asmGen(VarDec tree, int offset, boolean isParam) {

    }

    // Expressions

    public void asmGen(Exp tree, int offset, boolean isAddr) {

        if (tree instanceof NilExp) {

        } else if (tree instanceof VarExp) {
            asmGen((VarExp)tree, offset, isAddr);
        } else if (tree instanceof CallExp) {
            asmGen((CallExp)tree, offset);
        } else if (tree instanceof OpExp) {
            asmGen((OpExp)tree, offset);
        } else if (tree instanceof AssignExp) {
            asmGen((AssignExp)tree, offset);
        }  else if (tree instanceof IfExp) {
            asmGen((IfExp)tree, offset);
        } else if (tree instanceof WhileExp) {
            asmGen((WhileExp)tree, offset);
        } else if (tree instanceof ReturnExp) {
            asmGen((ReturnExp)tree, offset);
        } else if (tree instanceof CompoundExp) {
            asmGen((CompoundExp)tree, offset);
        } else if (tree instanceof IntExp) {
            asmGen((IntExp)tree);
        }
        return offset;
    }

    // Simple vars

    public void asmGen(SimpleVar tree, int offset, boolean isAddr) {

    }

    // Index vars

    public void asmGen(IndexVar tree, int offset, boolean isAddr) {
        
    }

    // Function decs

    public void asmGen(FunctionDec tree) {
        
    }

    // Varexp

    public void asmGen(VarExp tree, int offset, boolean isAddr) {
        
    }

    // IntExp

    public void asmGen(IntExp tree) {
        
    }

    // CallExp

    public void asmGen(CallExp tree, int offset) {
        
    }

    // OpExp

    public void asmGen(OpExp tree, int offset) {
        
    }

    // AssignExp

    public void asmGen(AssignExp tree, int offset) {
        
    }

    // IfExp

    public void asmGen(IfExp tree, int offset) {
        
    }

    // WhileExp

    public void asmGen(WhileExp tree, int offset) {
        
    }

    // ReturnExp

    public void asmGen(ReturnExp tree, int offset) {
        
    }
    
    // CompoundExp
    
    public void asmGen(CompoundExp tree, int offset) {
        
    }

    // Helpers for code gen

    public writeComment(String comment) {
        comment = "* " + comment + "\n";
        writeCode(comment);
    }

    public void writeCode(String contents) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(this.filename, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        outputStream.printf(contents);
        outputStream.close();
    }

}
