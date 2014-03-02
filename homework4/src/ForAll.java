public class ForAll extends Quantifier {

    public ForAll(Variable var, Expression expr) {
        super(var, expr);
    }

    @Override
    public String toString() {
        return "@" + super.toString();
    }
}
