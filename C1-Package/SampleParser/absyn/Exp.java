package absyn;

abstract public class Exp extends Absyn {
    NilExp(int pos)
    IntExp(int pos, int value)
    BoolExp(int pos, boolean value)
    VarExp(int pos, Var variable)
    CallExp(int pos, String func, ExpList args)
    OpExp(int pos, Exp left, int op, Exp right)
    AssignExp(int pos, VarExp lhs, Exp rhs)
    IfExp(int pos, Exp test, Exp then, Exp else)
    WhileExp(int pos, Exp test, Exp body)
    ReturnExp(int pos, Exp exp)
    CompoundExp(int pos, VarDecList decs, ExpList exps)
}
