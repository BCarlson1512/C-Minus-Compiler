package absyn;

public class Type extends Absyn {

    public final static int VOID = 0;
    public final static int INT = 1;
    public final static int BOOL = 2;

    public int type;

    public Type(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public void accept(AbsynVisitor visitor, int level) {
        visitor.visit(this, level);
    }
}
