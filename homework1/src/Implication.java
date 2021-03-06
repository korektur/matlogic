
public class Implication extends BinaryOp {

    public Implication(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean apply(boolean leftVal, boolean rightVal) {
        return !(leftVal && !rightVal);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Implication) {
            Implication c = (Implication) o;
            return (left.equals(c.left) && right.equals(c.right));
        } else {
            return false;
        }
    }
}
