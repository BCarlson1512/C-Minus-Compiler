package absyn;

public class IfExp extends Exp {
  public Exp test;
  public Exp thenpart;
  public Exp elsepart;

    /**
    *    @param row -> (int) row location in the program
    *    @param col -> (int) column location on row in the program
    *    @param thenpart -> (Exp) an expression following the if condition
    *    @param elsepart -> (Exp) The else part of an expression (can be null)
    */
  public IfExp( int row, int col, Exp test, Exp thenpart, Exp elsepart ) {
    this.row = row;
    this.col = col;
    this.test = test;
    this.thenpart = thenpart;
    this.elsepart = elsepart;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

