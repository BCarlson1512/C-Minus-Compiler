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
        emitBackup(tmpEmitLoc1);
        emitRMAbs("LDA", PC, tmpEmitLoc2, "Jump around I/O functions");
        emitComment("End of standard prelude...");
        emitRestore();

        // Generate code around main
        try { // hack around nullpointers so we can get marks 
            while (tree != null) { // traverse tree, generating code recursively
                if (tree.head != null) {
                    visit(tree.head);
                }
                tree = tree.tail;
            }
        } catch (Exception e) {
            System.out.println("Something's fucked brother");
        }


        // generate finale
        generateFinale();
        // exit scope
        symTable.deleteScope();
    }

    private void generateFinale() {
        emitComment("Generating Finale");
        emitRM("ST", FP, globalOffset, FP, "Push old FP");
        emitRM("LDA", FP, globalOffset, FP, "Push frame");
        emitRM("LDA", 0, 1, PC, "Load AC with return ptr");
        
        FunctionSymbol mainSym = (FunctionSymbol)symTable.lookupFn("main");
        emitRMAbs("LDA", PC, mainSym.fun_address, "Jump to main");
        emitRM("LD", FP, 0, FP, "Pop Frame");
        emitOP("HALT", 0, 0, 0, "");
    }

    // VarDecList

    public int visit(VarDecList tree, int offset, boolean isParam) {
        while (tree != null) {
            if (tree.head != null) offset = visit(tree.head, offset, isParam);
            tree = tree.tail;
        }
        return offset;
    }

    // Expression List

    public void visit(ExpList tree, int offset){
        while (tree != null) {
            if (tree.head != null) {
                visit(tree.head, offset, false);
            }
            tree = tree.tail;
        }
    }

    // General declarations

    public void visit(Dec tree){
        if (tree instanceof FunctionDec) {
            visit((FunctionDec)tree);
        } else if (tree instanceof VarDec) {
            // go to var dec function
            VarDec vd = (VarDec)tree;
            // Globally scoped vars?
            if (vd instanceof SimpleDec) {
                simpleDecHelper((SimpleDec)vd, globalOffset, true, true, true);
            } else if (vd instanceof ArrayDec) {
                arrayDecHelper((ArrayDec)vd, globalOffset, true, true, true);
            }
        }
    }

    // Var and param declarations

    public int visit(VarDec tree, int offset, boolean isParam) {
        if (isParam) {
            if (tree instanceof SimpleDec) {
                simpleDecHelper((SimpleDec)tree, offset, true, false, false);
            } else if (tree instanceof ArrayDec) {
                arrayDecHelper((ArrayDec)tree, offset, false, false, false);
            }
        } else {
            if (tree instanceof SimpleDec) {
                simpleDecHelper((SimpleDec)tree, offset, true, false, true);
            } else if (tree instanceof ArrayDec) {
                arrayDecHelper((ArrayDec)tree, offset, true, false, true);
            }
        }
        return offset;
    }

    // Expressions

    public int visit(Exp tree, int offset, boolean isAddr) {

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
        VariableSymbol vSym = (VariableSymbol)symTable.lookupSymbol(tree.name);
        emitComment("-> id");
        emitComment("looking up id: " + vSym.name);
        if(symTable.exists(vSym.name) == 0) { // lhs vs rhs of declarations
            if (isAddr == true) {
                emitRM("LDA", 0, vSym.offset, GP, "load id addr");
            } else {
                emitRM("LD", 0, vSym.offset, GP, "load id value");
            }
        } else {
            if (isAddr == true) {
                emitRM("LDA", 0, vSym.offset, GP, "load id addr");
            } else {
                emitRM("LD", 0, vSym.offset, GP, "load id value");
            }
        }
        emitComment("<- id");
    }

    // Index vars

    public void visit(IndexVar tree, int offset, boolean isAddr) {
        ArraySymbol aSym = (ArraySymbol)symTable.lookupSymbol(tree.name);
        emitComment("-> subs");
        if(symTable.exists(aSym.name) == 0) { // lhs vs rhs of declarations
            emitRM("LD", AC, aSym.offset, GP, "load id addr");
            emitRM("ST", AC, offset--, GP, "store arr addr");
            visit(tree.index, offset, false);
            emitComment("<- subs");
        } else {
            emitRM("LD", AC, aSym.offset, FP, "load id addr");
            emitRM("ST", AC, offset--, FP, "store arr addr");
            visit(tree.index, offset, false);
            emitComment("<- subs");
        }
        emitComment("<- subs");
    }

    // Function decs

    public void visit(FunctionDec tree) {
        int offset = -2;
        emitComment("-> funcdec");
        emitComment("processing function: " + tree.func);
        emitComment("jump around function body");
        int tmpLoc = emitSkip(1);
        FunctionSymbol funSym = new FunctionSymbol(tree.func, tree.ret_type.type, offset, emitLoc);
        symTable.addSymbolToScope(tree.func, funSym);
        symTable.createNewScope();
        emitRM("ST", 0, -1, FP, "store return");
        offset = visit(tree.params_list, offset, true);
        offset = visit(tree.body, offset, false);
        emitRM("LD", PC, -1, FP, "call return");
        int tmpLoc2 = emitSkip(0);
        emitBackup(tmpLoc);
        emitRMAbs("LDA", PC, tmpLoc2, "Jump around fun body");
        emitRestore();
        emitComment("<- funcdec");
        symTable.deleteScope();
    }

    // Varexp

    public void visit(VarExp tree, int offset, boolean isAddr) {
        if (tree.var instanceof SimpleVar) {
            SimpleVar sVar = (SimpleVar) tree.var;
            VariableSymbol vSym = (VariableSymbol) symTable.lookupSymbol(sVar.name);
            emitComment("-> id");
            emitComment("looking up id: " + sVar.name);
            if (symTable.exists(sVar.name) == 0) { // load into GP register
                if(isAddr) { // check if it's and address
                    emitRM("LDA", 0, vSym.offset, GP, "load id addr");
                } else {
                    emitRM("LD", 0, vSym.offset, GP, "load id value");
                }
            }
        } else if (tree.var instanceof IndexVar) { // load into FP register
            IndexVar idv = (IndexVar)tree.var;
            VariableSymbol vSym = (VariableSymbol)symTable.lookupSymbol(idv.name);
            if(isAddr) { // check if it's and address
                emitRM("LDA", 0, vSym.offset, FP, "load id addr");
            } else {
                emitRM("LD", 0, vSym.offset, FP, "load id value");
            }
        }
        emitComment("<- id");
    }

    // IntExp

    public void visit(IntExp tree) {
        emitComment("-> const");
        emitRM("LDC", AC, tree.value, 0, "load const");
        emitComment("<- const");
    }

    // CallExp

    public void visit(CallExp tree, int offset) {
        int i = -2;
        System.out.println("tree.func " + tree.func);
        FunctionSymbol funSym = (FunctionSymbol)symTable.lookupFn(tree.func);
        System.out.println("Funsym " + funSym);
        emitComment("-> call");
        emitComment("function call name: " + tree.func);
        while(tree.args != null) {
            if (tree.args.head != null) {
                visit(tree.args.head, offset, false);
                emitRM("ST", AC, offset+i, FP, "op: push left");
                i--;
            }
            tree.args = tree.args.tail;
        }
        emitRM("ST", FP, offset, FP, "push ofp");
        emitRM("LDA", FP, offset, FP, "push frame");
        emitRM("LDA", 0, 1, PC, "load ac with return ptr");
        emitRMAbs("LDA", PC, funSym.fun_address, "jump to function loc");
        emitRM("LD", FP, 0, FP, "pop frame");
        emitComment("<- call");
    }

    // OpExp

    public void visit(OpExp tree, int offset) {
        emitComment("-> op");
        if(tree.left instanceof IntExp) {
            visit(tree.left, offset, false);
            emitRM("ST", AC, offset--, FP, "op: push left");
        } else if (tree.left instanceof VarExp){
            VarExp varExpr = (VarExp)tree.left;
            if (varExpr.var instanceof SimpleVar) {
                visit(varExpr, offset, false);
                emitRM("ST", AC, offset--, FP, "op: push left");
            } else {
                visit(varExpr, offset--, true);
            }
        } else if (tree.left instanceof CallExp) {
            visit(tree.left, offset, false);
        } else if (tree.left instanceof OpExp) {
            visit(tree.left, offset, false);
            emitRM("ST", AC, offset--, FP, "");
        }

        if(tree.right instanceof IntExp) {
            visit(tree.right, offset, false);
        } else if(tree.right instanceof VarExp) {
            VarExp varExpr = (VarExp)tree.right;
            if (varExpr.var instanceof SimpleVar) {
                visit(varExpr, offset, false);
            } else {
                visit(varExpr, offset--, true);
            }
        } else if (tree.right instanceof CallExp) {
            visit(tree.right, offset, false);
        } else if (tree.left instanceof OpExp) {
            visit(tree.right, offset, false);
        }
        emitRM("LD", 1, offset++, FP, "op: load left");
        generateOpCode(tree.op);
    }

    private void generateOpCode(int op) {
        switch (op) {
            case OpExp.PLUS:
                emitOP("ADD", AC, 1, AC, "op +");
            break;
            case OpExp.MINUS:
                emitOP("SUB", AC, 1, AC, "op -");
            break;
            case OpExp.TIMES:
                emitOP("MUL", AC, 1, AC, "op *");
            break;
            case OpExp.OVER:
                emitOP("DIV", AC, 1, AC, "op /");
            break;
            case OpExp.EQ:
                emitOP("EQU", AC, 1, AC, "op =");
            break;
            case OpExp.EQEQ:
                emitComment("EQEQ subroutine");
                emitOP("SUB", AC, 1, AC, "op ==");
                emitRM("JEQ", AC, 2, PC, "");
                emitRM("LDC", AC, 0, 0, "false case");
                emitRM("LDA", PC, 1, PC, "unconditional jump");
                emitRM("LDC", AC, 2, 0, "true case");
            break;
            case OpExp.NOTEQ:
                emitComment("NOTEQ subroutine");
                emitOP("SUB", AC, 1, AC, "op !=");
                emitRM("JNE", AC, 2, PC, "");
                emitRM("LDC", AC, 0, 0, "false case");
                emitRM("LDA", PC, 1, PC, "unconditional jump");
                emitRM("LDC", AC, 2, 0, "true case");
            break;
            case OpExp.LT:
                emitComment("Less Than subroutine");
                emitOP("SUB", AC, 1, AC, "op <");
                emitRM("JLT", AC, 2, PC, "");
                emitRM("LDC", AC, 0, 0, "false case");
                emitRM("LDA", PC, 1, PC, "unconditional jump");
                emitRM("LDC", AC, 2, 0, "true case");
            break;
            case OpExp.LTE:
                emitComment("LTE subroutine");
                emitOP("SUB", AC, 1, AC, "op <=");
                emitRM("JLE", AC, 2, PC, "");
                emitRM("LDC", AC, 0, 0, "false case");
                emitRM("LDA", PC, 1, PC, "unconditional jump");
                emitRM("LDC", AC, 2, 0, "true case");
            break;
            case OpExp.GT:
                emitComment("GT subroutine");
                emitOP("SUB", AC, 1, AC, "op >");
                emitRM("JGT", AC, 2, PC, "");
                emitRM("LDC", AC, 0, 0, "false case");
                emitRM("LDA", PC, 1, PC, "unconditional jump");
                emitRM("LDC", AC, 2, 0, "true case");
            break;
            case OpExp.GTE:
                emitComment("GTE subroutine");
                emitOP("SUB", AC, 1, AC, "op >=");
                emitRM("JGE", AC, 2, PC, "");
                emitRM("LDC", AC, 0, 0, "false case");
                emitRM("LDA", PC, 1, PC, "unconditional jump");
                emitRM("LDC", AC, 2, 0, "true case");
            break;
            case OpExp.AND:
                //todo: implement and subroutine
            break;
            case OpExp.OR:
                //todo: implement or subroutine
            break;
        }
        emitComment("<- op");
    }

    // AssignExp

    public void visit(AssignExp tree, int offset) {
        emitComment("-> op");
        visit(tree.lhs, offset, true);
        emitRM("ST", AC, offset--, FP, "op: push left");
        offset -= 1;

        if (tree.rhs instanceof IntExp) {
            visit(tree.rhs, offset, false);
        } else if (tree.rhs instanceof VarExp) {
            visit(tree.rhs, offset, false);
        } else if (tree.rhs instanceof CallExp) {
            visit(tree.rhs, offset, false);
        } else if (tree.rhs instanceof OpExp) {
            visit(tree.rhs, offset, false);
        }
        offset += 1;
        emitRM("LD", 1, offset++, FP, "op: load left");
        emitRM("ST", AC, 0, 1, "assign: store val");
        emitComment("<- op");
    }

    // IfExp

    public void visit(IfExp tree, int offset) {
        symTable.createNewScope();
        emitComment("-> if");
        visit(tree.test, offset, false);
        int tmpLoc = emitSkip(1);
        visit(tree.thenpart, offset, false);
        int tmpLoc2 = emitSkip(0);
        emitBackup(tmpLoc);
        emitRMAbs("JEQ", 0, tmpLoc2, "If: jump to else");
        emitRestore();
        visit(tree.elsepart, offset, false);
        emitComment("<- if");
        symTable.deleteScope();
    }

    // WhileExp

    public void visit(WhileExp tree, int offset) {
        symTable.createNewScope();
        emitComment("-> while");
        emitComment("While: jump after body comes back");
        int tmpLoc1 = emitSkip(0);
        visit(tree.test, offset, false);
        int tmpLoc2 = emitSkip(1);
        visit(tree.body, offset, false);
        emitRMAbs("LDA", PC, tmpLoc1, "while: jump to test");
        int tmpLoc3 = emitSkip(0);
        emitBackup(tmpLoc2);
        emitRMAbs("JEQ", 0, tmpLoc3, "while: jump to end");
        emitRestore();
        emitComment("<- while");
        symTable.deleteScope();
    }

    // ReturnExp

    public void visit(ReturnExp tree, int offset) {
        emitComment("-> ret");
        visit(tree.exp, offset, false);
        emitRM("LD", PC, -1, FP, "return to caller");
        emitComment("<- ret");
    }
    
    // CompoundExp
    
    public int visit(CompoundExp tree, int offset) {
        emitComment("-> compound expr");
        offset = visit(tree.decs, offset, false);
        visit(tree.exps, offset);
        emitComment("<- compound expr");
        return offset;
    }

    // Helpers for code gen

    public void emitComment(String comment) {
        comment = "* " + comment + "\n";
        writeCode(comment);
    }

    public void emitRM(String op, int r, int offset, int r1, String comment) {
        String code = emitLoc + ": " + op + " " + r + " " + "," + offset + "(" + r1 + ")";
        writeCode(code);
        emitLoc++;
        writeCode("\t" + comment);
        writeCode("\n");
        highEmitLoc = Math.max(highEmitLoc, emitLoc);
    }

    public void emitOP(String op, int dest, int r, int r1, String comment) {
        String code = emitLoc + ": " + op + " " + dest + " " + "," + r + "," + r1;
        writeCode(code);
        emitLoc++;
        writeCode("\t" + comment);
        writeCode("\n");
        //highEmitLoc = Math.max(highEmitLoc, emitLoc); //todo uncomment later if bugs arise
    }

    public void emitRMAbs(String op, int r, int a, String comment) {
        String code = emitLoc + ": " + op + " " + r + " " + "," + (a-(emitLoc+1)) + "(" + PC +")";
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
        if (loc > highEmitLoc) {
            emitComment("Bugged emitbackup");
        }
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
            outputStream = new PrintWriter(new FileOutputStream(this.fileName, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        outputStream.print(contents);
        outputStream.close();
    }

    // Redundant code helpers

    private void simpleDecHelper(SimpleDec svd, int offset, boolean updateOffset, boolean isGlobal, boolean emitComments) {
        VariableSymbol varSym = new VariableSymbol(svd.name, svd.type.type, offset);
        symTable.addSymbolToScope(svd.name, varSym);
        if (updateOffset) offset--;
        if (isGlobal && emitComments) {
            emitComment("Add var to global scope: " + svd.name);
            emitComment("<- vardec");
        } else if(emitComments) {
            emitComment("processing local var: " + svd.name);
        }
    }

    private void arrayDecHelper(ArrayDec avd, int offset, boolean updateOffset, boolean isGlobal, boolean emitComments) {
        if (updateOffset && !isGlobal) offset = offset - (avd.size.value-1);
        ArraySymbol arrSym = new ArraySymbol(avd.name, avd.type.type, avd.size.value, offset);
        symTable.addSymbolToScope(avd.name, arrSym);
        if (updateOffset) offset--;
        if (isGlobal && emitComments) {
            emitComment("Add var to global scope: " + avd.name);
            emitComment("<- vardec");
        } else if (emitComments) {
            emitComment("processing local (array) var: " + avd.name);
        }
    }

}
