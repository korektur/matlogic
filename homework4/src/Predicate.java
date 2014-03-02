import java.util.ArrayList;
import java.util.Map;

public class Predicate implements Expression {

    protected String name;
    protected ArrayList<Expression> terms;

    public Predicate(String name, ArrayList<Expression> terms) {
        this.name = name;
        this.terms = terms;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> map) {
        return true;
    }

    @Override
    public String toString() {
        String s = name;
        if (terms != null && terms.size() != 0) {
            s += "(";
        }
        for (int i = 0; i < terms.size(); ++i) {
            s += terms.get(i);
            if (i < terms.size() - 1) {
                s += ",";
            }
        }
        if (terms != null && terms.size() != 0) {
            s += ")";
        }
        return s;
    }

    public boolean isTerm() {
        return Character.isLowerCase(name.charAt(0));
    }

}
