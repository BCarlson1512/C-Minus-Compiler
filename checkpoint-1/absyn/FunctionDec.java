package absyn;

public class FunctionDec extends Dec {
    public String func;
    public VarDecList params_list;
    public Exp body;
    public String res;

    /**
    *    @param row -> (int) row location in the program
    *    @param col -> (int) column location on row in the program
    *    @param res -> (Str) the return type?
    *    @param func -> (Str) the function name?
    *    @param params_list -> (VarDecList) a list of declared parameters
    *    @param Body -> (Exp) The function body (can be null)
    */
    public FunctionDec(int row, int col, String res, String func, VarDecList params_list, Exp body) {
        this.row = row;
        this.col = col;
        this.func = func;
        this.params_list = params_list;
        this.body = body;
        this.res = res;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
