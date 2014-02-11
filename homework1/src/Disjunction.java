/**
 * Created by Руслан on 11.02.14.
 */
public class Disjunction extends BinaryOp {

    public Disjunction(Expression left, Expression right){
        super(left, right);
    }

    @Override
    protected boolean apply(boolean leftVal, boolean rightVal){
        return (leftVal || rightVal);
    }
}
