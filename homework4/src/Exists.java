public class Exists extends Quantifier {

    public Exists(Variable var, Expression expr) {
        super(var, expr);
    }

    @Override
    public String toString() {
        return "?" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Exists) && super.equals(o);
    }
}
