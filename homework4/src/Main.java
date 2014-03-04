import java.io.*;
import java.util.ArrayList;

public class Main {

    public static Expression getExchange(Expression ex1, Expression ex2) {
        if (ex1 instanceof Variable) {
            if (!ex2.equals(ex1))
                return ex2;
            else return null;
        }
        if (ex1 instanceof BinaryOp && ex2 instanceof BinaryOp) {
            BinaryOp op1 = (BinaryOp) ex1;
            BinaryOp op2 = (BinaryOp) ex2;
            Expression getAns = getExchange(op1.getLeft(), op2.getLeft());
            if (getAns != null) {
                return getAns;
            }
            return getExchange(op1.getRight(), op2.getRight());
        }
        if (ex1 instanceof Quantifier && ex2 instanceof Quantifier) {
            Quantifier q1 = (Quantifier) ex1;
            Quantifier q2 = (Quantifier) ex2;
            Expression getAns = getExchange(q1.getVar(), q2.getVar());
            if (getAns != null)
                return getAns;
            return getExchange(q1.getExpr(), q2.getExpr());
        }
        if (ex1 instanceof Predicate && ex2 instanceof Predicate) {
            Predicate p1 = (Predicate) ex1;
            Predicate p2 = (Predicate) ex2;
            ArrayList<Expression> terms1 = p1.getTerms();
            ArrayList<Expression> terms2 = p2.getTerms();
            if (terms1.size() != terms2.size())
                return null;
            for (int i = 0; i < terms1.size(); ++i) {
                Expression ans = getExchange(terms1.get(i), terms2.get(i));
                if (ans != null)
                    return ans;
            }
            return null;
        }
        return null;
    }

    public static ArrayList<Variable> getVariables(Expression expr) {
        ArrayList<Variable> ans = new ArrayList<>();
        if (expr instanceof Variable) {
            ans.add((Variable) expr);
        }
        if (expr instanceof BinaryOp) {
            BinaryOp op = (BinaryOp) expr;
            ans.addAll(getVariables(op.getLeft()));
            ans.addAll(getVariables(op.getRight()));
        }
        if (expr instanceof Quantifier) {
            Quantifier q = (Quantifier) expr;
            ans.add(q.getVar());
            ans.addAll(getVariables(q.getExpr()));
        }
        if (expr instanceof Predicate) {
            Predicate p = (Predicate) expr;
            ArrayList<Expression> terms = p.getTerms();
            for (Expression term : terms) {
                ans.addAll(getVariables(term));
            }
        }
        return ans;
    }

    public static ArrayList<Variable> getChainedVariables(Expression expr) {
        ArrayList<Variable> ans = new ArrayList<>();
        if (expr instanceof BinaryOp) {
            BinaryOp op = (BinaryOp) expr;
            ans.addAll(getChainedVariables(op.getLeft()));
            ans.addAll(getChainedVariables(op.getRight()));
        }
        if (expr instanceof Quantifier) {
            Quantifier q = (Quantifier) expr;
            ans.add(q.getVar());
        }
        if (expr instanceof Predicate) {
            Predicate p = (Predicate) expr;
            ArrayList<Expression> terms = p.getTerms();
            for (Expression term : terms) {
                ans.addAll(getChainedVariables(term));
            }
        }
        return ans;
    }

    public static ArrayList<Variable> getFreeVariables(Expression expr) {
        ArrayList<Variable> all = getVariables(expr);
        ArrayList<Variable> chained = getChainedVariables(expr);
        ArrayList<Variable> ans = new ArrayList<>();
        for (Variable v : all) {
            if (!chained.contains(v)) {
                ans.add(v);
            }
        }
        return ans;
    }

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
        String[] arg = s.split(",");
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
        int i;
        ans = new ArrayList<>();
        boolean isGood = true;
        ArrayList<ArrayList<Variable>> freeVariables = new ArrayList<>();
        for (Expression e : added) {
            freeVariables.add(getFreeVariables(e));
        }
        freeVariables.add(getFreeVariables(alpha));
        try {
            for (i = 0; i < input.size() && isGood; ++i) {
                Expression expr = input.get(i);
                isGood = false;
                int checker = Axioms.checker(expr);
                if (checker != -1) {
                    if (checker >= 11) {
                        Variable v;
                        if (checker == 11) {
                            ForAll forAll = (ForAll) ((Implication) expr).getLeft();
                            v = forAll.getVar();
                            ArrayList<Variable> v1 = getVariables(forAll.getExpr());
                            v1.add(forAll.getVar());
                            Expression term = ((Implication)expr).getRight();
                            term = getExchange(forAll.getExpr(), term);
                            ArrayList<Variable> v2 = getVariables(getExchange(forAll.getExpr(), term));
                            for(Variable x : v2) {
                                if (v1.contains(v2)){
                                    throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                    "терм " + term.toString() + "не свободен для подстановки в формулу " +
                                    forAll.getExpr().toString() + " вместо переменной " + forAll.getVar().toString());
                                }
                            }
                        } else {
                            Exists exists = (Exists) ((Implication) expr).getRight();
                            v = exists.getVar();
                            ArrayList<Variable> v1 = getVariables(exists.getExpr());
                            v1.add(exists.getVar());
                            Expression term = ((Implication)expr).getLeft();
                            term = getExchange(exists.getExpr(), term);
                            ArrayList<Variable> v2 = getVariables(getExchange(exists.getExpr(), term));
                            for(Variable x : v2) {
                                if (v1.contains(v2)){
                                    throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                            "терм " + term.toString() + "не свободен для подстановки в формулу " +
                                            exists.getExpr().toString() + " вместо переменной " + exists.getVar().toString());
                                }
                            }
                        }
                        for (int j = 0; j < freeVariables.size(); ++j) {
                            if (freeVariables.get(j).contains(v)) {
                                Expression e = alpha;
                                if (j < added.size())
                                    e = added.get(j);
                                throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                        "используется схема аксиом с квантором по переменной " + v.toString() +
                                        ", входящей свободно в допущение " + e.toString());
                            }
                        }

                    }
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
                if (isGood)
                    continue;
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
                                    if (getVariables(impl.getLeft()).contains(forAll.getVar())
                                            && !getChainedVariables(impl.getLeft()).contains(forAll.getVar())) {
                                        throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": "
                                                + "переменная " + forAll.getVar().toString() + " входит свободно в формулу " +
                                                impl.getLeft().toString());
                                    }
                                    Variable v = forAll.getVar();
                                    for (int k = 0; k < freeVariables.size(); ++k) {
                                        if (freeVariables.get(k).contains(v)) {
                                            Expression e = alpha;
                                            if (k < added.size())
                                                e = added.get(k);
                                            throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                                    "используется схема аксиом с квантором по переменной " + v.toString() +
                                                    ", входящей свободно в допущение " + e.toString());
                                        }
                                    }
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
                                    if (getVariables(impl.getRight()).contains(exists.getVar())
                                            && !getChainedVariables(impl.getRight()).contains(exists.getVar())) {
                                        throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": "
                                                + "переменная " + exists.getVar().toString() + " входит свободно в формулу " +
                                                impl.getRight().toString());
                                    }
                                    Variable v = exists.getVar();
                                    for (int k = 0; k < freeVariables.size(); ++k) {
                                        if (freeVariables.get(k).contains(v)) {
                                            Expression e = alpha;
                                            if (k < added.size())
                                                e = added.get(k);
                                            throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                                    "используется схема аксиом с квантором по переменной " + v.toString() +
                                                    ", входящей свободно в допущение " + e.toString());
                                        }
                                    }
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
            }
            if (isGood) {
                for (Expression expr : ans) {
                    out.println(expr.toString());

                }
            } else {
                out.print("Вывод некорректен начиная с формулы " + i);

            }
        } catch (Exception e) {
            //e.printStackTrace();
            out.println(e.getMessage());
        }
        out.close();
    }

}
