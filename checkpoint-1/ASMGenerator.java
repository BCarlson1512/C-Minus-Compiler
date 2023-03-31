import absyn.*;
import symbol.*;
import java.io.*;
import java.util.ArrayList;


public class ASMGenerator {

    // Special registers
    public static final int PC = 7;
    public static final int GP = 6;
    public static final int FP = 5;
    public static final int AC = 0;
    public static final int AC1 = 1;

    public String fileName;

    // emit locations
    public int emitLoc = 0;
    public int highEmitLoc = 0;
    public int globalOffset = 0;

    // Absyn + sym table requirements
    private SymbolTable symTable;
    private DecList program;

    public ASMGenerator(DecList program, String filename) {
        this.program = program;
        this.fileName = filename;
        symTable = new SymbolTable(false);
    }

    // driver function
    public void generate() {
        generateASM(this.program);
    }

    // wrapper for all other codegen functions
    public void generateASM(DecList tree) {

        // Setup sym table
        symTable.createNewScope();
        // input and output functions

        // Generate Prelude
        emitComment("Generating Standard Prelude");
        emitRM("LD", GP, 0, AC, "Load GP with max addr");
        emitRM("LDA", FP, 0, GP, "Copy GP to FP");
        emitRM("ST", 0, 0, 0, "Clear location 0");
        int tmpEmitLoc1 = emitSkip(1);

        // generate input function

        emitComment("Jump around I/O Functions");
        emitComment("Input routine");

        emitRM("ST", 0, -1, FP, "Store return");
        emitOP("IN", 0, 0, 0, "");
        emitRM("LD", PC, -1, FP, "Call return");

        // generate output function
        emitComment("Output routine");

        emitRM("ST", 0, -1, FP, "Store return");
        emitRM("LD", 0, -2, FP, "Load output value");
        emitOP("OUT", 0, 0, 0, "");
        emitRM("LD", 7,-1,FP, "Call return");
        int tmpEmitLoc2 = emitSkip(0);
        
        // jump around I/O functions
        emitBackup(savedLoc);
        emitRMAbs("LDA", PC, tmpEmitLoc2, "Jump around I/O functions");
        emitComment("End of standard prelude...");
        emitRestore();

        // Generate code around main

        while (tree != null) { // traverse tree, generating code recursively
            if (tree.head != null) {
                visit(tree.head);
            }
            tree = tree.tail;
        }

        // generate finale
        FunctionSymbol mainSym = (FunctionSymbol)symTable.getFunction("main");
        emitComment("Generating Finale");
        emitRM("ST", FP, globalOffset, FP, "Push old FP");
        emitRM("LDA", FP, globalOffset, FP, "Push frame");
        emitRM("LDA", 0, 1, PC, "Load AC with return ptr");

        // get main address from sym table
        emitRMAbs("LDA", PC, mainSym.address, "Jump to main");
        emitRM("LD", FP, 0, FP, "Pop Frame");
        emitOP("HALT", 0, 0, 0, "");
        // exit scope
        symTable.deleteScope();
    }

    // VarDecList

    public void visit(VarDecList tree, int offset, boolean isParam) {

    }

    // Expression List

    public void visit(ExpList tree, int offset){

    }

    // General declarations

    public void visit(Dec tree){

    }

    // Var and param declarations

    public void visit(VarDec tree, int offset, boolean isParam) {

    }

    // Expressions

    public void visit(Exp tree, int offset, boolean isAddr) {

        if (tree instanceof NilExp) {

        } else if (tree instanceof VarExp) {
            visit((VarExp)tree, offset, isAddr);
        } else if (tree instanceof CallExp) {
            visit((CallExp)tree, offset);
        } else if (tree instanceof OpExp) {
            visit((OpExp)tree, offset);
        } else if (tree instanceof AssignExp) {
            visit((AssignExp)tree, offset);
        }  else if (tree instanceof IfExp) {
            visit((IfExp)tree, offset);
        } else if (tree instanceof WhileExp) {
            visit((WhileExp)tree, offset);
        } else if (tree instanceof ReturnExp) {
            visit((ReturnExp)tree, offset);
        } else if (tree instanceof CompoundExp) {
            visit((CompoundExp)tree, offset);
        } else if (tree instanceof IntExp) {
            visit((IntExp)tree);
        }
        return offset;
    }

    // Simple vars

    public void visit(SimpleVar tree, int offset, boolean isAddr) {

    }

    // Index vars

    public void visit(IndexVar tree, int offset, boolean isAddr) {
        
    }

    // Function decs

    public void visit(FunctionDec tree) {
        
    }

    // Varexp

    public void visit(VarExp tree, int offset, boolean isAddr) {
        
    }

    // IntExp

    public void visit(IntExp tree) {
        
    }

    // CallExp

    public void visit(CallExp tree, int offset) {
        
    }

    // OpExp

    public void visit(OpExp tree, int offset) {
        
    }

    // AssignExp

    public void visit(AssignExp tree, int offset) {
        
    }

    // IfExp

    public void visit(IfExp tree, int offset) {
        
    }

    // WhileExp

    public void visit(WhileExp tree, int offset) {
        
    }

    // ReturnExp

    public void visit(ReturnExp tree, int offset) {
        
    }
    
    // CompoundExp
    
    public void visit(CompoundExp tree, int offset) {
        
    }

    // Helpers for code gen

    public void emitComment(String comment) {
        comment = "* " + comment + "\n";
        writeCode(comment);
    }

    public void emitRM(Stirng op, int r, int offset, int r1, String comment) {
        String code = emitLoc + ": " + op + " " + r " " + "," + offset + "(" + r1 +")";
        writeCode(code);
        emitLoc++;
        writeCode("\t" + comment);
        writeCode("\n");
        highEmitLoc = Math.max(highEmitLoc, emitLoc);
    }

    public void emitOP(Stirng op, int dest, int r, int r1, String comment) {
        String code = emitLoc + ": " + op + " " + dest " " + "," + r + "," + r1;
        writeCode(code);
        emitLoc++;
        writeCode("\t" + comment);
        writeCode("\n");
        //highEmitLoc = Math.max(highEmitLoc, emitLoc); //uncomment later if bugs arise
    }

    public void emitRMAbs(String op, int r, int a, String comment) {
        String code = emitLoc + ": " + op + " " + r " " + "," + (a-(emitLoc+1)) + "(" + PC +")";
        writeCode(code);
        emitLoc++;
        writeCode("\t" + comment);
        writeCode("\n");
        highEmitLoc = Math.max(highEmitLoc, emitLoc);
    }

    public void emitRestore() {
        emitLoc = highEmitLoc;
    }

    public void emitBackup(int loc){ 
        emitLoc = loc;
    }

    // skip portions of code, return existing label value
    public int emitSkip(int dist) {
        int i = emitLoc;
        emitLoc += dist;
        highEmitLoc = Math.max(highEmitLoc, emitLoc);
        return i;
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
