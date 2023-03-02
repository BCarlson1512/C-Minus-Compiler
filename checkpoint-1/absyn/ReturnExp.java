package absyn;

public class ReturnExp extends Exp {
    public Exp exp;
    /**
    *    @param row -> (int) row location in the program
    *    @param col -> (int) column location on row in the program
    *    @param Exp -> (Exp) an expression, can be null
    */
    public ReturnExp(int row, int col, Exp exp) {
        this.row = row;
        this.col = col;
        this.exp = exp;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
