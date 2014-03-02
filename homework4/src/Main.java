import java.io.*;
import java.util.ArrayList;

public class Main {

    private static Expression replace(Expression expr, Expression a, Expression b, Expression c, Variable v) {
        if (expr instanceof Predicate) {
            Predicate predicate = (Predicate) expr;
            if ("A".equals(predicate.getName()))
                return a;
            if ("B".equals(predicate.getName()))
                return b;
            if ("C".equals(predicate.getName()))
                return c;
        }

        if (expr instanceof Variable) {
            return expr;
        }

        if (expr instanceof Conjunction) {
            Conjunction conj = (Conjunction) expr;
            return new Conjunction(replace(conj.getLeft(), a, b, c, v), replace(conj.getRight(), a, b, c, v));
        }

        if (expr instanceof Disjunction) {
            Disjunction disj = (Disjunction) expr;
            return new Disjunction(replace(disj.getLeft(), a, b, c, v), replace(disj.getRight(), a, b, c, v));
        }

        if (expr instanceof Implication) {
            Implication impl = (Implication) expr;
            return new Implication(replace(impl.getLeft(), a, b, c, v), replace(impl.getRight(), a, b, c, v));
        }

        if (expr instanceof Negation) {
            Negation neg = (Negation) expr;
            return new Negation(replace(neg.getExpr(), a, b, c, v));
        }

        if (expr instanceof Exists) {
            Exists exists = (Exists) expr;
            return new Exists(v, replace(exists.getExpr(), a, b, c, v));
        }

        if (expr instanceof ForAll) {
            ForAll forAll = (ForAll) expr;
            forAll = new ForAll(v, replace(forAll.getExpr(), a, b, c, v));
            return forAll;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        ArrayList<Expression> ans;
        Expression alpha;
        PrintWriter out = new PrintWriter("output.txt");
        ArrayList<Expression> input = new ArrayList<>();
        String s = in.readLine();
        String[] arg = s.split(" ");
        ArrayList<Expression> added = new ArrayList<>();
        for (int i = 0; i < arg.length - 1; ++i) {
            added.add((new Parser(arg[i])).parse());
        }
        String[] todo = arg[arg.length - 1].split("\\|-");
        alpha = (new Parser(todo[0])).parse();
        s = in.readLine();
        while (s != null) {
            input.add((new Parser(s)).parse());
            s = in.readLine();
        }

        ArrayList<Expression> mpExsist = new ArrayList<>();
        in = new BufferedReader(new FileReader("MPExist.in"));
        s = in.readLine();
        while (s != null) {
            mpExsist.add((new Parser(s)).parse());
            s = in.readLine();
        }

        ArrayList<Expression> mpForAll = new ArrayList<>();
        in = new BufferedReader(new FileReader("MPForAll.in"));
        s = in.readLine();
        while (s != null) {
            mpForAll.add(new Parser(s).parse());
            s = in.readLine();
        }

        ans = new ArrayList<>();
        boolean isGood = true;
        for (int i = 0; i < input.size() && isGood; ++i) {
            Expression expr = input.get(i);
            isGood = false;
            if (Axioms.checker(expr) != -1) {
                ans.add(expr);
                ans.add(new Implication(expr, new Implication(alpha, expr)));
                ans.add(new Implication(alpha, expr));
                isGood = true;
                continue;
            }
            for (Expression g : added) {
                if (g.equals(expr)) {
                    ans.add(expr);
                    ans.add(new Implication(expr, new Implication(alpha, expr)));
                    ans.add(new Implication(alpha, expr));
                    isGood = true;

                }
                if (isGood) break;
            }
            if (alpha.equals(expr)) {
                ans.add(new Implication(alpha, new Implication(alpha, alpha)));
                ans.add(new Implication(new Implication(alpha, new Implication(alpha, alpha)),
                        new Implication(new Implication(alpha, new Implication(
                                new Implication(alpha, alpha), alpha)), new Implication(alpha, alpha))));
                ans.add(new Implication(new Implication(alpha, new Implication(new Implication(alpha, alpha), alpha)),
                        new Implication(alpha, alpha)));
                ans.add(new Implication(alpha, new Implication(new Implication(alpha, alpha), alpha)));
                ans.add(new Implication(alpha, alpha));
                isGood = true;
                continue;
            }

            for (int j = i - 1; j >= 0; --j) {
                Expression mp = input.get(j);
                if (mp instanceof Implication) {
                    Implication impl = (Implication) mp;
                    if (impl.getRight().equals(expr)) {
                        for (int k = i - 1; k >= 0; --k) {
                            Expression mp2 = input.get(k);
                            if (mp2.equals(impl.getLeft())) {
                                ans.add(new Implication(new Implication(alpha, mp2), new Implication(new
                                        Implication(alpha, new Implication(mp2, expr)), new Implication(alpha, expr))));
                                ans.add(new Implication(new Implication(alpha, new Implication(mp2, expr)),
                                        new Implication(alpha, expr)));
                                ans.add(new Implication(alpha, expr));
                                isGood = true;
                                break;
                            }
                        }
                    }
                    if (isGood)
                        break;
                }
            }
            if (isGood) {
                continue;
            }

            if (expr instanceof Implication) {
                Implication impl = (Implication) expr;
                if (impl.getRight() instanceof ForAll) {
                    ForAll forAll = (ForAll) impl.getRight();
                    for (int j = i - 1; j >= 0; --j) {
                        if (input.get(j) instanceof Implication) {
                            Implication impl2 = (Implication) input.get(j);
                            if (impl.getLeft().equals(impl2.getLeft()) && forAll.getExpr().equals(impl2.getRight())) {
                                for (Expression ex : mpForAll) {
                                    ans.add(replace(ex, alpha, forAll.getExpr(), impl.getLeft(), forAll.getVar()));
                                }
                                isGood = true;
                                break;
                            }
                        }
                    }
                }
            }

            if (isGood) {
                continue;
            }

            if (expr instanceof Implication) {
                Implication impl = (Implication) expr;
                if (impl.getLeft() instanceof Exists) {
                    Exists exists = (Exists) impl.getLeft();
                    for (int j = i - 1; j >= 0; --j) {
                        if (input.get(j) instanceof Implication) {
                            Implication impl2 = (Implication) input.get(j);
                            if (impl.getRight().equals(impl2.getRight()) && exists.getExpr().equals(impl2.getLeft())) {
                                for (Expression ex : mpExsist) {
                                    ans.add(replace(ex, alpha, exists.getExpr(), impl.getRight(), exists.getVar()));
                                }
                                isGood = true;
                                break;
                            }
                        }
                    }
                }
            }
            System.out.println(i);
        }
        if (isGood) {
            for (Expression expr : ans) {
                out.println(expr.toString());
            }
        } else {
            out.println("Error");
        }
        out.close();
    }

}
