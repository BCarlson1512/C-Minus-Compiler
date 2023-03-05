package absyn;

public interface AbsynVisitor {

  public void visit( ExpList exp, int level );

  public void visit( AssignExp exp, int level );

  public void visit( IfExp exp, int level );

  public void visit( IntExp exp, int level );

  public void visit( OpExp exp, int level );

  public void visit( ReadExp exp, int level );

  public void visit( RepeatExp exp, int level );

  public void visit( VarExp exp, int level );

  public void visit( WriteExp exp, int level );

  public void visit( ArrayDec expList, int level);

  public void visit( CallExp exp, int level);

  public void visit( DecList decList, int level);

  public void visit( Dec declaration, int level);

  public void visit( Exp expr, int level);

  public void visit( FunctionDec expr, int level);

  public void visit( NilExp expr, int level);

  public void visit( ReturnExp expr, int level);

  public void visit( SimpleDec expr, int level);

  public void visit( SimpleVar expr, int level);

  public void visit( Var expr, int level);

  public void visit( VarDec expr, int level);

  public void visit( VarDecList expr, int level);

  public void visit( VarExp expr, int level);

  public void visit( WhileExp expr, int level);

  public void visit( CompoundExp expr, int level);
}
