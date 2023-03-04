package absyn;

public class OpExp extends Exp {
  public final static int PLUS   = 0;
  public final static int MINUS  = 1;
  public final static int TIMES  = 2;
  public final static int OVER   = 3;
  public final static int EQ     = 4;
  public final static int NOTEQ = 5;
  public final static int EQEQ = 6;
  public final static int LT     = 7;
  public final static int GT     = 8;
  public final static int GTE = 9;
  public final static int LTE = 10;

  // TODO unsure about these
  public final static int AND = 11;
  public final static int OR = 12;
  public final static int NOt = 13;

  public Exp left;
  public int op;
  public Exp right;

  /**
  *    @param row -> (int) row location in the program
  *    @param col -> (int) column location on row in the program
  *    @param left -> (Exp) an expression following the if condition
  *    @param op -> (Exp) Lhs part of an expression (can be null)
  *    @param right -> (Exp) The rhs part of an expression 
  */
  public OpExp( int row, int col, Exp left, int op, Exp right ) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.op = op;
    this.right = right;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
