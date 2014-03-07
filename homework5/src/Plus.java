public class Plus extends BinaryOp {

    public Plus(Expression left, Expression right) {
        super(left, right);
    }

    public boolean apply(boolean a, boolean b) {
        return true;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Plus) {
            Plus p = (Plus) o;
            return p.getLeft().equals(left) && p.getRight().equals(right);
        }
        return false;
    }
}
