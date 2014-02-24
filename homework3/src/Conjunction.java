
public class Conjunction extends BinaryOp {

    public Conjunction(Expression left, Expression right) {
        super(left, right);
    }

    AOverride
    protected boolean apply(boolean leftVal, boolean rightVal) {
        return (leftVal && rightVal);
    }

    AOverride
    public boolean equals(Object o) {
        if (o instanceof Conjunction) {
            Conjunction c = (Conjunction) o;
            return (left.equals(c.left) && right.equals(c.right));
        } else {
            return false;
        }
    }

    AOverride
    public String toString(){
        return "(" + left.toString() + "&" + right.toString() + ")";
    }

}
