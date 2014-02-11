
public class Parser {

    private class Token {
        Expression acc;
        String rest;

        public Token(Expression acc, String rest) {
            this.acc = acc;
            this.rest = rest;
        }
    }

    private Token PlusMinus(String s) {
        Token current = MulDiv(s);
        Expression acc = current.acc;

        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '&' || current.rest.charAt(0) == '|')) {
                break;
            }

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = MulDiv(next);
            if (sign == '&') {
                acc = new Conjunction(acc, current.acc);
            } else {
                acc = new Disjunction(acc, current.acc);
            }
        }
        return new Token(acc, current.rest);
    }

    private Token Bracket(String s) {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Token r = PlusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            }
            return r;
        }
        return Num(s);
    }

    private Token MulDiv(String s) {
        Token current = Bracket(s);
        Expression acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign1 = current.rest.charAt(0);
            char sign2 = sign1;
            if (current.rest.length() > 1) {
                sign2 = current.rest.charAt(1);
            }
            if (!(sign1 == '-' && sign2 == '>')) {
                return current;
            }

            String next = current.rest.substring(2);
            Token right = Bracket(next);

            acc = new Implication(acc, right.acc);
            current = new Token(acc, right.rest);
        }
    }

    private Token Num(String s) {
        int i = 0;
        boolean negative = false;
        if (s.charAt(0) == '!') {
            negative = true;
            s = s.substring(1);
        }

        String var = "";
        while (i < s.length() && (Character.isAlphabetic(s.charAt(i)) || Character.isDigit(s.charAt(i)))) {
            var += s.charAt(i);
            i++;
        }
        Expression ans = new Variable(var);
        if (negative) {
            ans = new Negation(ans);
        }
        return new Token(ans, s.substring(i));

    }

    public Expression Parse(String s) {
        return PlusMinus(s.replace(" ", "")).acc;
    }

}
