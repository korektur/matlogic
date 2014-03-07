public class Equals extends BinaryOp {

    public Equals(Expression left, Expression right) {
        super(left, right);
    }

    public boolean apply(boolean a, boolean b) {
        return a == b;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "=" + right.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Equals) {
            Equals e = (Equals) o;
            return (e.getLeft().equals(left)) && (e.getRight().equals(right));
        }
        return false;
    }
}
