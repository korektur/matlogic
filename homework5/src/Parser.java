import java.util.ArrayList;

public class Parser {
    private String expr;

    public Parser(String expr) {
        this.expr = expr.replaceAll("->", ">");
    }

    private Expression impl(int l, int r) {
        int balance = 0;
        for (int i = l; i < r; ++i) {
            if (expr.charAt(i) == '(') {
                ++balance;
            } else if (expr.charAt(i) == ')') {
                --balance;
            } else if (expr.charAt(i) == '>' && balance == 0) {
                return new Implication(disj(l, i), impl(i + 1, r));
            }
        }
        return disj(l, r);
    }

    private Expression disj(int l, int r) {
        int balance = 0;
        int index = -1;
        for (int i = l; i < r; ++i) {
            if (expr.charAt(i) == '(') {
                ++balance;
            } else if (expr.charAt(i) == ')') {
                --balance;
            } else if (expr.charAt(i) == '|' && balance == 0) {
                index = i;
            }
        }
        if (index != -1) {
            return new Disjunction(disj(l, index), conj(index + 1, r));
        }
        return conj(l, r);
    }

    private Expression conj(int l, int r) {
        int balance = 0;
        int index = -1;
        for (int i = l; i < r; ++i) {
            if (expr.charAt(i) == '(') {
                ++balance;
            } else if (expr.charAt(i) == ')') {
                --balance;
            } else if (expr.charAt(i) == '&' && balance == 0) {
                index = i;
            }
        }
        if (index != -1) {
            return new Conjunction(conj(l, index), unary(index + 1, r));
        }
        return unary(l, r);
    }

    private Expression unary(int l, int r) {
        if (expr.charAt(l) == '!') {
            return new Negation(unary(l + 1, r));
        }
        if (expr.charAt(l) == '(' && expr.charAt(r - 1) == ')') {
            boolean fl = true;
            int balance = 1;
            for (int i = l + 1; i < r - 1; ++i) {
                if (expr.charAt(i) == '(') {
                    ++balance;
                }
                if (expr.charAt(i) == ')') {
                    --balance;
                }
                if (balance == 0)
                    fl = false;
            }
            if (fl)
                return impl(l + 1, r - 1);
        }
        if (expr.charAt(l) == '@') {
            Variable v = (Variable) var(l + 1, r);
            return new ForAll(v, unary(l + 1 + v.toString().length(), r));
        }
        if (expr.charAt(l) == '?') {
            Variable v = (Variable) var(l + 1, r);
            return new Exists(v, unary(l + 1 + v.toString().length(), r));
        }
        return pred(l, r);
    }

    private Expression pred(int l, int r) {
        if (Character.isAlphabetic(expr.charAt(l)) && Character.isUpperCase(expr.charAt(l))) {
            String name = "";
            int index = l;
            while (index < r && (Character.isDigit(expr.charAt(index)) || (Character.isAlphabetic(expr.charAt(index))
                    && Character.isUpperCase(expr.charAt(index))))) {
                name += expr.charAt(index++);
            }
            ArrayList<Expression> terms = new ArrayList<>();
            ++index;
            int ll = index;
            while (index <= r - 1) {
                if (expr.charAt(index) == ',' || index == r - 1) {
                    terms.add(term(ll, index));
                    ll = index + 1;
                }
                ++index;
            }
            return new Predicate(name, terms);
        } else {
            for (int i = l; i < r; ++i) {
                if (expr.charAt(i) == '=') {
                    return new Equals(term(l, i), term(i + 1, r));
                }
            }
        }
        return null;
    }

    private Expression term(int l, int r) {
        int index = -1;
        int balance = 0;
        for (int i = l; i < r; ++i) {
            if (expr.charAt(i) == ')') {
                --balance;
            } else if (expr.charAt(i) == '(') {
                ++balance;
            } else if (expr.charAt(i) == '+' && balance == 0) {
                index = i;
            }
        }
        if (index != -1) {
            return new Plus(term(l, index), plus(index + 1, r));
        }
        return plus(l, r);
    }

    private Expression plus(int l, int r) {
        int index = -1;
        int balance = 0;
        for (int i = l; i < r; ++i) {
            if (expr.charAt(i) == ')') {
                --balance;
            } else if (expr.charAt(i) == '(') {
                ++balance;
            } else if (expr.charAt(i) == '*' && balance == 0) {
                index = i;
            }
        }
        if (index != -1) {
            return new Times(plus(l, index), times(index + 1, r));
        }
        return times(l, r);
    }

    private Expression times(int l, int r) {
        if (expr.charAt(r - 1) == '\'') {
            return new Apostrophe(times(l, r - 1));
        }
        if (expr.charAt(l) == '0') {
            return new Zero();
        }

        if (expr.charAt(l) == '(') {
            return term(l + 1, r - 1);
        }
        String name = var(l, r).toString();
        if (l + name.length() == r) {
            return new Variable(name);
        }
        ArrayList<Expression> terms = new ArrayList<>();
        int index = l + name.length();
        ++index;
        int ll = index;
        while (index <= r - 1) {
            if (expr.charAt(index) == ',' || index == r - 1) {
                terms.add(term(ll, index));
                ll = index + 1;
            }
            ++index;
        }
        return new Predicate(name, terms);
    }

    private Expression var(int l, int r) {
        String name = "";
        int index = l;
        while (index < r && (Character.isDigit(expr.charAt(index)) || (Character.isAlphabetic(expr.charAt(index))
                && Character.isLowerCase(expr.charAt(index))))) {
            name += expr.charAt(index++);
        }
        return new Variable(name);
    }

    public Expression parse() {
        return impl(0, expr.length());
    }
}