import java.util.ArrayList;

public class Parser {
    private String expr;
    private ArrayList<String> variables;

    public Parser(String expr) {
        this.expr = expr.replaceAll("->", ">");
        this.variables = new ArrayList<String>();
    }

    public ArrayList<String> getVariables() {
        return variables;
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

        if (expr.charAt(begin) == '!') {
            return new Negation(parse(begin + 1, end));
        }
        if (Character.isAlphabetic(expr.charAt(begin))){
            String s = "";
            int i = begin;
            while(i < end && (Character.isAlphabetic(expr.charAt(i)) || Character.isDigit(expr.charAt(i)))){
                s += expr.charAt(i);
                ++i;
            }
            if (!variables.contains(s)){
                variables.add(s);
            }
            return new Variable(s);
        }
        if (expr.charAt(begin) == '('){
            return parse(begin + 1, end - 1);
        }

        return null;
    }

    public Expression parse() {
        return parse(0, expr.length());
    }
}