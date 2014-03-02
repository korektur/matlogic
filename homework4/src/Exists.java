public class Exists extends Quantifier{

    public Exists(Variable name, Expression expr) {
        super(name, expr);
    }

    @Override
    public String toString() {
        return "?" + super.toString();
    }
}
