import java.util.Map;

public class Apostrophe implements Expression{

    private Expression expr;

    public Apostrophe(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpr() {
        return expr;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> map) {
        return expr.evaluate(map);
    }

    @Override
    public String toString() {
        return expr.toString() + "'";
    }

    @Override
    public  boolean equals(Object o) {
        if (o instanceof Apostrophe) {
            Apostrophe a = (Apostrophe) o;
            return a.expr.equals(expr);
        }
        return false;
    }
}
