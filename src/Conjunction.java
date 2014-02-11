
public class Conjunction extends BinaryOp {

    public Conjunction(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean apply(boolean leftVal, boolean rightVal) {
        return (leftVal && rightVal);
    }

}
