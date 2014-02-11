import java.util.Map;

/**
 * Created by Руслан on 11.02.14.
 */
public abstract class BinaryOp implements Expression {

    private Expression left;
    private Expression right;

    public BinaryOp(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var){
        return apply(left.evaluate(var), right.evaluate(var));
    }

    protected abstract boolean apply(boolean leftVal, boolean rightVal);

}
