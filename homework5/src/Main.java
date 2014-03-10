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
        if (ex1 instanceof Negation && ex2 instanceof Negation) {
            Negation neg1 = (Negation) ex1;
            Negation neg2 = (Negation) ex2;
            return getExchange(neg1.getExpr(), neg2.getExpr());
        }
        if (ex1 instanceof Apostrophe && ex2 instanceof Apostrophe) {
            Apostrophe ap1 = (Apostrophe) ex1;
            Apostrophe ap2 = (Apostrophe) ex2;
            return getExchange(ap1.getExpr(), ap2.getExpr());
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
        if (expr instanceof Negation) {
            return getVariables(((Negation) expr).getExpr());
        }
        if (expr instanceof Apostrophe) {
            return getVariables(((Apostrophe) expr).getExpr());
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
        if (expr instanceof Negation) {
            return getChainedVariables(((Negation) expr).getExpr());
        }
        if (expr instanceof Apostrophe) {
            return getChainedVariables(((Apostrophe) expr).getExpr());
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        ArrayList<Expression> proof = new ArrayList<>();
        String s = in.readLine();
        while (s != null) {
            proof.add((new Parser(s)).parse());
            s = in.readLine();
        }
        int i;
        boolean isGood = true;
        try {
            for (i = 0; i < proof.size() && isGood; ++i) {
                Expression expr = proof.get(i);
                isGood = false;
                int checker = Axioms.checker(expr);
                if (checker != -1) {
                    if (checker >= 11 && checker <= 13) {
                        if (checker == 11) {
                            ForAll forAll = (ForAll) ((Implication) expr).getLeft();
                            ArrayList<Variable> v1 = getChainedVariables(forAll);
                            Expression term = ((Implication) expr).getRight();
                            term = getExchange(forAll.getExpr(), term);
                            ArrayList<Variable> v2 = getVariables(term);
                            if (!forAll.getVar().equals(term) && term != null) {
                                for (Variable x : v2) {
                                    if (v1.contains(x)) {
                                        throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                                "терм " + term.toString() + "не свободен для подстановки в формулу " +
                                                forAll.getExpr().toString() + " вместо переменной " + forAll.getVar().toString());
                                    }
                                }
                            }
                        } else if (checker == 12) {
                            Exists exists = (Exists) ((Implication) expr).getRight();
                            ArrayList<Variable> v1 = getChainedVariables(exists);
                            v1.add(exists.getVar());
                            Expression term = ((Implication) expr).getLeft();
                            term = getExchange(exists.getExpr(), term);
                            ArrayList<Variable> v2 = getVariables(term);
                            if (term != null && !exists.getVar().equals(term)) {
                                for (Variable x : v2) {
                                    if (v1.contains(x)) {
                                        throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                                "терм " + term.toString() + "не свободен для подстановки в формулу " +
                                                exists.getExpr().toString() + " вместо переменной " + exists.getVar().toString());
                                    }
                                }
                            }
                        } else {
                            Implication impl = (Implication) expr;
                            Conjunction conj = (Conjunction) impl.getLeft();
                            ForAll forAll = (ForAll) conj.getRight();
                            Variable v = forAll.getVar();
                            ArrayList<Variable> vars = getChainedVariables(impl.getRight());
                            if (vars.contains(v)){
                                throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": " +
                                        "терм " + (new Apostrophe(v)) + "не свободен для подстановки в формулу " +
                                        impl.getRight() + " вместо переменной " + v);
                            }
                        }

                    }
                    isGood = true;
                    continue;
                }
                for (int j = i - 1; j >= 0; --j) {
                    Expression mp = proof.get(j);
                    if (mp instanceof Implication) {
                        Implication impl = (Implication) mp;
                        if (impl.getRight().equals(expr)) {
                            for (int k = i - 1; k >= 0; --k) {
                                Expression mp2 = proof.get(k);
                                if (mp2.equals(impl.getLeft())) {
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
                            if (proof.get(j) instanceof Implication) {
                                Implication impl2 = (Implication) proof.get(j);
                                if (impl.getLeft().equals(impl2.getLeft()) && forAll.getExpr().equals(impl2.getRight())) {
                                    if (getVariables(impl.getLeft()).contains(forAll.getVar())
                                            && !getChainedVariables(impl.getLeft()).contains(forAll.getVar())) {
                                        throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": "
                                                + "переменная " + forAll.getVar().toString() + " входит свободно в формулу " +
                                                impl.getLeft().toString());
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
                            if (proof.get(j) instanceof Implication) {
                                Implication impl2 = (Implication) proof.get(j);
                                if (impl.getRight().equals(impl2.getRight()) && exists.getExpr().equals(impl2.getLeft())) {
                                    if (getVariables(impl.getRight()).contains(exists.getVar())
                                            && !getChainedVariables(impl.getRight()).contains(exists.getVar())) {
                                        throw new Exception("Вывод некорректен начиная с формулы " + (i + 1) + ": "
                                                + "переменная " + exists.getVar().toString() + " входит свободно в формулу " +
                                                impl.getRight().toString());
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
                out.println("Доказательство корректно");
            } else {
                out.print("Вывод некорректен начиная с формулы " + i);

            }
        } catch (Exception e) {
            //e.printStackTrace();
            out.println(e.getMessage());
        }
        out.close();
        out.close();
    }

}
