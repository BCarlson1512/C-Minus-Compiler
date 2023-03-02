package absyn;

public abstract class VarDec extends Dec {
    public String type;
    public String name;

    SimpleDec(int pos, NameTy typ, String name)
    ArrayDec(int pos, NameTy typ, String name, int size)
    // misc classes
    DecList(Dec head, DecList tail)
    VarDecList(VarDec head, VarDecList tail)
    ExpList(Exp head, ExpList tail)
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}