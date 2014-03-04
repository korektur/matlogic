import java.security.Permission;
import java.util.ArrayList;

public class Axioms {

    private static boolean check1(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getRight();
                return (impl1.getLeft().equals(impl2.getRight()));
            }
        }
        return false;
    }

    private static boolean check2(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getLeft();
                if (impl1.getRight() instanceof Implication) {
                    Implication impl3 = (Implication) impl1.getRight();
                    if (impl3.getLeft() instanceof Implication) {
                        Implication impl4 = (Implication) impl3.getLeft();
                        if (impl4.getRight() instanceof Implication) {
                            Implication impl5 = (Implication) impl4.getRight();
                            if (impl3.getRight() instanceof Implication) {
                                Implication impl6 = (Implication) impl3.getRight();
                                boolean fl1 = impl2.getLeft().equals(impl4.getLeft()) && impl2.getLeft().equals(impl6.getLeft());
                                boolean fl2 = impl2.getRight().equals(impl5.getLeft());
                                boolean fl3 = impl5.getRight().equals(impl6.getRight());
                                return (fl1 && fl2 && fl3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean check3(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getRight();
                if (impl2.getRight() instanceof Conjunction) {
                    Conjunction conj1 = (Conjunction) impl2.getRight();
                    boolean fl1 = impl1.getLeft().equals(conj1.getLeft());
                    boolean fl2 = impl2.getLeft().equals(conj1.getRight());
                    return (fl1 && fl2);
                }
            }
        }
        return false;
    }

    private static boolean check4(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Conjunction) {
                Conjunction conj1 = (Conjunction) impl1.getLeft();
                return (impl1.getRight().equals(conj1.getLeft()));
            }
        }
        return false;
    }

    private static boolean check5(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Conjunction) {
                Conjunction conj1 = (Conjunction) impl1.getLeft();
                return (impl1.getRight().equals(conj1.getRight()));
            }
        }
        return false;
    }

    private static boolean check6(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Disjunction) {
                Disjunction disj1 = (Disjunction) impl1.getRight();
                return (impl1.getLeft().equals(disj1.getLeft()));
            }
        }
        return false;
    }

    private static boolean check7(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getRight() instanceof Disjunction) {
                Disjunction disj1 = (Disjunction) impl1.getRight();
                return (impl1.getLeft().equals(disj1.getRight()));
            }
        }
        return false;
    }

    private static boolean check8(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getLeft();
                if (impl1.getRight() instanceof Implication) {
                    Implication impl3 = (Implication) impl1.getRight();
                    if (impl3.getLeft() instanceof Implication) {
                        Implication impl4 = (Implication) impl3.getLeft();
                        if (impl3.getRight() instanceof Implication) {
                            Implication impl5 = (Implication) impl3.getRight();
                            if (impl5.getLeft() instanceof Disjunction) {
                                Disjunction disj1 = (Disjunction) impl5.getLeft();
                                boolean fl1 = impl2.getLeft().equals(disj1.getLeft());
                                boolean fl2 = impl2.getRight().equals(impl4.getRight()) && impl2.getRight().equals(impl5.getRight());
                                boolean fl3 = impl4.getLeft().equals(disj1.getRight());
                                return (fl1 && fl2 && fl3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean check9(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication)e;
            if (impl1.getLeft() instanceof Implication){
                Implication impl2 = (Implication)impl1.getLeft();
                if(impl1.getRight() instanceof Implication){
                    Implication impl3 = (Implication)impl1.getRight();
                    if (impl3.getLeft() instanceof Implication){
                        Implication impl4 = (Implication)impl3.getLeft();
                        if (impl4.getRight() instanceof Negation){
                            Negation neg1 = (Negation) impl4.getRight();
                            if (impl3.getRight() instanceof Negation) {
                                Negation neg2 = (Negation) impl3.getRight();
                                boolean fl1 = neg2.getExpr().equals(impl2.getLeft());
                                fl1 = fl1 && neg2.getExpr().equals(impl4.getLeft());
                                boolean fl2 = impl2.getRight().equals(neg1.getExpr());
                                return (fl1 && fl2);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean check10(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Negation) {
                Negation neg1 = (Negation) impl1.getLeft();
                if (neg1.getExpr() instanceof Negation) {
                    Negation neg2 = (Negation) neg1.getExpr();
                    return impl1.getRight().equals(neg2.getExpr());
                }
            }
        }
        return false;
    }

    private static boolean check11(Expression e) {
        if (e instanceof Implication) {
            Implication impl = (Implication) e;
            if (impl.getLeft() instanceof ForAll) {
                ForAll forall = (ForAll) e;
                Expression expr = Main.getExchange(forall.getExpr(), impl.getRight());
                if(expr != null && change(forall.getExpr(), expr, forall.getVar()).equals(impl.getRight())){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check12(Expression e) {
        if (e instanceof Implication) {
            Implication impl = (Implication)e;
            if (impl.getRight() instanceof Exists) {
                Exists exists = (Exists) impl.getRight();
                Expression expr = Main.getExchange(exists.getExpr(), impl.getLeft());
                if (expr != null && change(exists.getExpr(), expr, exists.getVar()).equals(impl.getLeft())){
                    return true;
                }
            }
        }
        return false;
    }

    public static Expression change(Expression e, Expression a, Expression x) {
        if (e instanceof Variable) {
            Variable v = (Variable) e;
            if (v.equals(x)){
                return a;
            }
            return v;
        }
        if (e instanceof Predicate) {
            Predicate p = (Predicate) e;
            ArrayList<Expression> terms = new ArrayList<>();
            for (Expression term : p.getTerms()) {
                terms.add(change(term, a, x));
            }
            return new Predicate(p.getName(), terms);
        }
        if (e instanceof Conjunction) {
            Conjunction conj = (Conjunction) e;
            return new Conjunction(change(conj.getLeft(), a, x), change(conj.getRight(), a, x));
        }

        if (e instanceof Disjunction) {
            Disjunction disj = (Disjunction) e;
            return new Disjunction(change(disj.getLeft(), a, x), change(disj.getRight(), a, x));
        }

        if (e instanceof Implication) {
            Implication impl = (Implication) e;
            return new Implication(change(impl.getLeft(), a, x), change(impl.getRight(), a, x));
        }

        if (e instanceof Negation) {
            Negation neg = (Negation) e;
            return new Negation(change(neg.getExpr(), a, x));
        }

        if (e instanceof Exists) {
            Exists exists = (Exists) e;
            return new Exists((Variable)change(exists.getVar(), a, x), change(exists.getExpr(), a, x));
        }

        if (e instanceof ForAll) {
            ForAll forAll = (ForAll) e;
            forAll = new ForAll((Variable)change(forAll.getVar(), a, x), change(forAll.getExpr(), a, x));
            return forAll;
        }
        return null;
    }


    public static int checker(Expression e){
        if (check1(e)) return 1;
        if (check2(e)) return 2;
        if (check3(e)) return 3;
        if (check4(e)) return 4;
        if (check5(e)) return 5;
        if (check6(e)) return 6;
        if (check7(e)) return 7;
        if (check8(e)) return 8;
        if (check9(e)) return 9;
        if (check10(e)) return 10;
        if (check11(e)) return 11;
        if (check12(e)) return 12;
        return -1;
    }
}
