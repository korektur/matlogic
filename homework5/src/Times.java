
public class Times extends BinaryOp {

    public Times(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected boolean apply(boolean a, boolean b) {
        return true;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Times) {
            Times m = (Times)o;
            return (m.getLeft().equals(left)) && m.getRight().equals(right);
        }
        return false;
    }

}
