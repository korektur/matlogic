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

}
