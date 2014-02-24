import java.util.Map;


public class Negation implements Expression {
    private Expression expr;

    public Negation(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpr() {
        return expr;
    }

    AOverride
    public boolean evaluate(Map<String, Boolean> var) {
        return !expr.evaluate(var);
    }

    AOverride
    public boolean equals(Object o) {
        if (o instanceof Negation) {
            Negation c = (Negation) o;
            return (expr.equals(c.expr));
        } else {
            return false;
        }
    }

    AOverride
    public String toString(){
        return "(" + "!" + expr.toString() + ")";
    }
}
