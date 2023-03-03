package absyn;

public abstract class VarDec extends Dec {
    public String type;
    public String name;

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}