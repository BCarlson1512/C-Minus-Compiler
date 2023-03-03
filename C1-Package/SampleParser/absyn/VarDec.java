package absyn;

abstract class VarDec extends Dec {
    SimpleDec(int pos, NameTy typ, String name)
    ArrayDec(int pos, NameTy typ, String name, int size)
    // misc classes
    DecList(Dec head, DecList tail)
    VarDecList(VarDec head, VarDecList tail)
    ExpList(Exp head, ExpList tail)
    
    // constants
    final static int OpExp.PLUS;
    final static int OpExp.MINUS;
    final static int OpExp.UMINUS;
    final static int OpExp.MUL;
    final static int OpExp.DIV;
    final static int OpExp.EQ;
    final static int OpExp.NE;
    final static int OpExp.LT;
    final static int OpExp.LE;
    final static int OpExp.GT;
    final static int OpExp.GE;
    final static int OpExp.NOT;
    final static int OpExp.AND;
    final static int OpExp.OR;

    // constants for type field NameTy

    final static int NameTY.BOOL;
    final static int NameTY.INT;
    final static int NameTY.VOID;
}