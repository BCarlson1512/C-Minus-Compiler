package absyn;

public class CallExp extends Exp {
    public ExpList args;
    public String func;

    public CallExp(int row, int col, ExpList args, String func) {
        this.row = row;
        this.col = col;
        this.args = args;
        this.func = func;
    }

    //TODO: Count params function

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
