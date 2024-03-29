import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ExpList expList, int level ) {
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp: " );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }

  public void visit( IfExp exp, int level ) {
    indent( level );
    System.out.println( "IfExp: " );
    level++;
    exp.test.accept( this, level );
    exp.thenpart.accept( this, level );
    if (exp.elsepart != null )
      exp.elsepart.accept( this, level );
  }

  public void visit( IntExp exp, int level ) {
    indent( level );
    System.out.println( "IntExp: " + exp.value );
    level++;
    indent(level);
    System.out.println(exp.value);
  }

  public void visit( OpExp exp, int level ) {
    indent( level );
    System.out.print( "OpExp: " ); 
    switch( exp.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      case OpExp.NOTEQ:
        System.out.println( " != " );
        break;
      case OpExp.EQEQ:
        System.out.println( " == " );
        break;
      case OpExp.GTE:
        System.out.println( " >= " );
        break;
      case OpExp.LTE:
        System.out.println( " <= " );
        break;
      default:
        System.out.println( "Unrecognized operator at ROW: " + exp.row + " COL: " + exp.col);
    }
    level++;
    if (exp.left != null)
      exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( ReadExp exp, int level ) {
    indent( level );
    System.out.println( "ReadExp: " );
    exp.input.accept( this, ++level );
  }

  public void visit( RepeatExp exp, int level ) {
    indent( level );
    System.out.println( "RepeatExp: " );
    level++;
    exp.exps.accept( this, level );
    exp.test.accept( this, level ); 
  }

  public void visit( WriteExp exp, int level ) {
    indent( level );
    System.out.println( "WriteExp: " );
    exp.output.accept( this, ++level );
  }

  public void visit( ArrayDec expList, int level) {
    indent(level);
    System.out.println( "ArrayDec: " );
    level++;
    visit(expList.type, level);
    indent(level);
    System.out.println("Array Name: " + expList.name);
    if(expList.size != null) { // only traverse to non-null nodes
      visit(expList.size, level);
    }
  }

  public void visit( CallExp exp, int level) {
    indent(level);
    System.out.println( "CallExp: " );
    level++;
    indent(level);
    System.out.println(exp.func);
    visit(exp.args, level);
  }

  public void visit( DecList decList, int level) {
    while(decList != null) { // account for null cases
      if (decList.head != null) {
        decList.head.accept(this, level);
      }
      decList = decList.tail;
    }
  }

  public void visit(Dec declaration, int level) {
    boolean isVarDec = declaration instanceof VarDec;
    boolean isFuncDec = declaration instanceof FunctionDec;
    if (isVarDec) { // is a variable declaration
      visit((VarDec)declaration, level);
    } else if (isFuncDec) { // is a func declaration
      visit((FunctionDec)declaration, level);
    } else { // is simple/Illegal declaration
      indent(level);
      System.out.println( "Illegal Declaration... Row: " + declaration.row + " Col: " + declaration.col );
    }
  }

  public void visit(Exp expr, int level) {
    // determine which type of Expression we are dealing with
    if (expr instanceof ReturnExp) {
      visit((ReturnExp)expr, level);
    } else if (expr instanceof CompoundExp) {
      visit((CompoundExp)expr, level);
    } else if (expr instanceof WhileExp) {
      visit((WhileExp)expr, level);
    } else if (expr instanceof IfExp) {
      visit((IfExp)expr, level);
    } else if (expr instanceof AssignExp) {
      visit((AssignExp)expr, level);
    } else if (expr instanceof OpExp) {
      visit((OpExp)expr, level);
    } else if (expr instanceof CallExp) {
      visit((CallExp)expr, level);
    } else if (expr instanceof IntExp) {
      visit((IntExp)expr, level);
    } else if (expr instanceof VarExp) {
      visit((VarExp)expr, level);
    } else if (expr instanceof BoolExp) {
      visit((BoolExp)expr, level);
    } else { // invalid expressions
      indent(level);
      System.out.println("Illegal Expression... Row: " + expr.row + " Col: " + expr.col);
    }
  }

  public void visit(BoolExp expr, int level) {
    indent(level);
    System.out.println( "BoolExp: " );  
    level++;
    indent(level);
    System.out.println(expr.value);
  }

  public void visit(FunctionDec expr, int level) {
    indent(level);
    System.out.println( "FunctionDec: " );
    level++;
    visit(expr.ret_type, level);
    indent(level);
    System.out.println("Function: " + expr.func);
    visit(expr.params_list, level);
    visit(expr.body, level);
  }

  public void visit(NilExp expr, int level) {
    indent(level);   
    System.out.println( "NilExp: " );
  }

  public void visit(ReturnExp expr, int level) {
    indent(level);
    System.out.println( "ReturnExp: " );  
    level++;
    if (expr.exp != null) {
      visit(expr.exp, level);
    }
  }

  public void visit(SimpleDec expr, int level) {
    indent(level);
    System.out.println( "SimpleDec: " );
    indent(level);
    visit(expr.type, level);
    level++;
    indent(level);
    System.out.println("SimpleDec name: " + expr.name);
  }

  public void visit(SimpleVar expr, int level) {
    indent(level);
    System.out.println( "SimpleVar: " );
    level++;
    indent(level);
    System.out.println("SimpleVar name: " + expr.name);
  }

  public void visit(IndexVar expr, int level) {
    indent(level);
    System.out.println( "IndexVar: " );
    level++;
    indent(level);
    System.out.println("IndexVar name: " + expr.name);
  }

  public void visit(Var expr, int level) {
    if (expr instanceof SimpleVar) {
      visit((SimpleVar)expr, level);
    } else if (expr instanceof IndexVar) {
      visit((IndexVar)expr, level);
    } else { // invalid cases
      indent(level);
      System.out.println("Illegal Expression/Var... Row: " + expr.row + " Col: " + expr.col);
    }
  }

  public void visit(VarDec expr, int level) {
    if (expr instanceof SimpleDec) {
      visit((SimpleDec)expr, level);
    } else if (expr instanceof ArrayDec) {
      visit((ArrayDec)expr, level);
    } else { // invalid cases
      indent(level);
      System.out.println("Illegal VarDeclaration... Row: " + expr.row + " Col: " + expr.col);
    }
  }

  public void visit(VarDecList expr, int level) {
    while(expr != null) {
      if (expr.head != null) {
        visit(expr.head, level);
      }
      expr = expr.tail;
    }
  }

  public void visit(VarExp expr, int level) {
    indent(level);
    System.out.println( "VarExp: " );
    level++;
    visit(expr.var, level);
  }

  public void visit(WhileExp expr, int level) {
    indent(level);
    System.out.println( "WhileExp: " );
    level++;
    visit(expr.test, level);
    visit(expr.body, level);
  }

  public void visit(CompoundExp expr, int level) {
    indent(level);
    System.out.println( "CompoundExp: " );
    level++;
    visit(expr.decs, level);
    visit(expr.exps, level);
  }

  public void visit (Type ty, int level) {
    indent(level);
    if (ty.type == Type.INT) {
      System.out.println( "Type: Integer");
    } else if(ty.type == Type.VOID) {
        System.out.println( "Type: Void");
    } else if(ty.type == Type.BOOL) {
        System.out.println( "Type: Boolean");
    }
  }
}
