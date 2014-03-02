import java.util.ArrayList;

public class Parser {
    private String expr;
    private ArrayList<String> variables;

    public Parser(String expr) {
        this.expr = expr.replaceAll("->", ">");
        this.variables = new ArrayList<>();
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    private ArrayList<Expression> findTerm(int begin, int end) {
        ArrayList<Expression> terms = new ArrayList<>();
        int i = begin;
        int balance = 1;
        while (i < end && balance != 0) {
            if (expr.charAt(i) == ')') {
                --balance;
            }
            if (expr.charAt(i) == '(') {
                ++balance;
            }
            if (Character.isAlphabetic(expr.charAt(i))) {
                String name = "";
                while (i < end && ((Character.isAlphabetic(expr.charAt(i)) || Character.isDigit(expr.charAt(i))))) {
                    name += expr.charAt(i);
                    ++i;
                }
                if (i < end && '(' == expr.charAt(i)) {
                    terms.add(new Predicate(name, findTerm(i + 1, end)));
                } else {
                    terms.add(new Variable(name));
                }
            }
            ++i;
        }
        return terms;
    }

    private Expression parse(int begin, int end) {

        int balance = 0;
        for (int i = begin; i < end; ++i) {
            if (expr.charAt(i) == '(') {
                balance++;
            }
            if (expr.charAt(i) == ')') {
                balance--;
            }
            if (expr.charAt(i) == '>' && balance == 0) {
                return new Implication(parse(begin, i), parse(i + 1, end));
            }
        }

        balance = 0;
        for (int i = begin; i < end; ++i) {
            if (expr.charAt(i) == '(') {
                balance++;
            }
            if (expr.charAt(i) == ')') {
                balance--;
            }
            if (expr.charAt(i) == '|' && balance == 0) {
                return new Disjunction(parse(begin, i), parse(i + 1, end));
            }
        }

        balance = 0;
        for (int i = begin; i < end; ++i) {
            if (expr.charAt(i) == '(') {
                balance++;
            }
            if (expr.charAt(i) == ')') {
                balance--;
            }
            if (expr.charAt(i) == '&' && balance == 0) {
                return new Conjunction(parse(begin, i), parse(i + 1, end));
            }
        }

        if (Character.isAlphabetic(expr.charAt(begin)) && Character.isUpperCase(expr.charAt(begin))) {
            String name = "";
            int i = begin;
            while (i < end && (Character.isAlphabetic(expr.charAt(i)) || Character.isDigit(expr.charAt(i)))) {
                name += expr.charAt(i);
                ++i;
            }
            ArrayList<Expression> terms = new ArrayList<>();
            if (i < end && '(' == expr.charAt(i)) {
                terms = findTerm(i + 1, end);
            }
            return new Predicate(name, terms);
        }

        if (expr.charAt(begin) == '@' || expr.charAt(begin) == '?') {
            int i = begin + 1;
            String name = "";
            while (i < end && ((Character.isAlphabetic(expr.charAt(i)) && Character.isLowerCase(expr.charAt(i)))
                    || Character.isDigit(expr.charAt(i)))) {
                name += expr.charAt(i);
                ++i;
            }
            if (expr.charAt(begin) == '@') {
                return new ForAll(new Variable(name), parse(i, end));
            } else {
                return new Exists(new Variable(name), parse(i, end));
            }
        }

        if (expr.charAt(begin) == '!') {
            return new Negation(parse(begin + 1, end));
        }

        if (Character.isAlphabetic(expr.charAt(begin)) && Character.isLowerCase(expr.charAt(begin))) {
            String s = "";
            int i = begin;
            while (i < end && (Character.isAlphabetic(expr.charAt(i)) || Character.isDigit(expr.charAt(i)))) {
                s += expr.charAt(i);
                ++i;
            }
            if (!variables.contains(s)) {
                variables.add(s);
            }
            return new Variable(s);
        }


        if (expr.charAt(begin) == '(') {
            return parse(begin + 1, end - 1);
        }

        return null;
    }

    public Expression parse() {
        return parse(0, expr.length());
    }
}