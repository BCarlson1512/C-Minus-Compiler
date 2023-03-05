package absyn;

public class SimpleVar extends Var {
    String name;
    public SimpleVar(String name, int row, int col) {
        this.row = row;
        this.col = col;
        this.name = name;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
