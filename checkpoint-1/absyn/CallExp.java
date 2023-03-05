package absyn;

public class CallExp extends Exp {
    public ExpList args;
    public String func;

    public CallExp(int row, int col, String func, ExpList args) {
        this.row = row;
        this.col = col;
        this.args = args;
        this.func = func;
    }
    // generates a count of parameters
    public int count_params() {
        ExpList temp = this.args;
        int paramsCount = 0;
        while (temp != null) {
            temp = temp.tail;
            count++;
        }
        return paramsCount;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
