import java.util.Map;


public class Negation implements Expression {
    private Expression expr;

    public Negation(Expression expr){
        this.expr = expr;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var){
        return !expr.evaluate(var);
    }
}
