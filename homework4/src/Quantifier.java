import java.util.Map;

public abstract class Quantifier implements Expression{

    protected Variable var;
    protected Expression expr;

    public Quantifier(Variable var, Expression expr) {
        this.var = var;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return var.toString() + expr.toString();
    }

    @Override
    public boolean evaluate(Map<String, Boolean> map) {
        return true;
    }

    public Variable getVar() {
        return var;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quantifier) {
            Quantifier q = (Quantifier) o;
            return var.equals(q.getVar()) && expr.equals(q.getExpr());
        }
        return false;
    }

}
