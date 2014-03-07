import java.util.ArrayList;
import java.util.Map;

public class Predicate implements Expression {

    private String name;
    private ArrayList<Expression> terms;

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
        if (terms != null) {
            for (int i = 0; i < terms.size(); ++i) {
                s += terms.get(i);
                if (i < terms.size() - 1) {
                    s += ",";
                }
            }
        }
        if (terms != null && terms.size() != 0) {
            s += ")";
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Predicate) {
            Predicate p = (Predicate) o;
            boolean fl = name.equals(p.getName()) && (terms.size() == p.getTerms().size());
            for (int i = 0; i < terms.size() && fl; ++i) {
                if (!terms.get(i).equals(p.getTerms().get(i))) {
                    fl = false;
                }
            }
            return fl;
        }
        return false;
    }

    //public boolean isTerm() {
    //    return Character.isLowerCase(name.charAt(0));
    //}

    public ArrayList<Expression> getTerms() {
        return terms;
    }

    public String getName() {
        return name;
    }
}
