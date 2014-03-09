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
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getLeft();
                if (impl1.getRight() instanceof Implication) {
                    Implication impl3 = (Implication) impl1.getRight();
                    if (impl3.getLeft() instanceof Implication) {
                        Implication impl4 = (Implication) impl3.getLeft();
                        if (impl4.getRight() instanceof Negation) {
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
                ForAll forall = (ForAll) impl.getLeft();
                Expression expr = Main.getExchange(forall.getExpr(), impl.getRight());
                if (expr == null) {
                    expr = forall.getVar();
                }
                Expression ee = change(forall.getExpr(), expr, forall.getVar());
                if (change(forall.getExpr(), expr, forall.getVar()).equals(impl.getRight())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check12(Expression e) {
        if (e instanceof Implication) {
            Implication impl = (Implication) e;
            if (impl.getRight() instanceof Exists) {
                Exists exists = (Exists) impl.getRight();
                Expression expr = Main.getExchange(exists.getExpr(), impl.getLeft());
                if (expr == null) {
                    expr = exists.getVar();
                }
                if (change(exists.getExpr(), expr, exists.getVar()).equals(impl.getLeft())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Expression change(Expression e, Expression a, Expression x) {
        if (e instanceof Variable) {
            Variable v = (Variable) e;
            if (v.equals(x)) {
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
            return new Exists((Variable) change(exists.getVar(), a, x), change(exists.getExpr(), a, x));
        }

        if (e instanceof ForAll) {
            ForAll forAll = (ForAll) e;
            forAll = new ForAll((Variable) change(forAll.getVar(), a, x), change(forAll.getExpr(), a, x));
            return forAll;
        }
        if (e instanceof Equals) {
            Equals eq = (Equals) e;
            return new Equals(change(eq.getLeft(), a, x), change(eq.getRight(), a, x));
        }
        if (e instanceof Plus) {
            Plus p = (Plus) e;
            return new Plus(change(p.getLeft(), a, x), change(p.getRight(), a, x));
        }
        if (e instanceof Times) {
            Times t = (Times) e;
            return new Times(change(t.getLeft(), a, x), change(t.getRight(), a, x));
        }
        if (e instanceof Zero) {
            return e;
        }
        if (e instanceof Apostrophe) {
            Apostrophe ap = (Apostrophe) e;
            return new Apostrophe(change(ap.getExpr(), a, x));
        }
        return null;
    }

    private static boolean checkA1(Expression e) {
        if (e instanceof Implication) {
            Implication impl = (Implication) e;
            if (impl.getLeft() instanceof Equals) {
                Equals eq1 = (Equals) impl.getLeft();
                if (impl.getRight() instanceof Equals) {
                    Equals eq2 = (Equals) impl.getRight();
                    if (eq2.getLeft() instanceof Apostrophe) {
                        Apostrophe a1 = (Apostrophe) eq2.getLeft();
                        if (eq2.getRight() instanceof Apostrophe) {
                            Apostrophe a2 = (Apostrophe) eq2.getRight();
                            if (eq1.getRight() instanceof Variable && eq1.getLeft() instanceof Variable) {
                                return eq1.getRight().equals(a2.getExpr()) && eq1.getLeft().equals(a1.getExpr());
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkA2(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Equals) {
                Equals eq1 = (Equals) impl1.getLeft();
                if (impl1.getRight() instanceof Implication) {
                    Implication impl2 = (Implication) impl1.getRight();
                    if (impl2.getLeft() instanceof Equals) {
                        Equals eq2 = (Equals) impl2.getLeft();
                        if (impl2.getRight() instanceof Equals) {
                            Equals eq3 = (Equals) impl2.getRight();
                            if (eq1.getLeft() instanceof Variable && eq1.getRight() instanceof Variable &&
                                    eq2.getRight() instanceof Variable) {
                                boolean fl1 = eq1.getLeft().equals(eq2.getLeft());
                                boolean fl2 = eq1.getRight().equals(eq3.getLeft());
                                boolean fl3 = eq2.getRight().equals(eq3.getRight());
                                return fl1 && fl2 && fl3;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkA3(Expression e) {
        if (e instanceof Implication) {
            Implication impl = (Implication) e;
            if (impl.getLeft() instanceof Equals) {
                Equals eq1 = (Equals) impl.getLeft();
                if (impl.getRight() instanceof Equals) {
                    Equals eq2 = (Equals) impl.getRight();
                    if (eq1.getLeft() instanceof Apostrophe) {
                        Apostrophe a1 = (Apostrophe) eq1.getLeft();
                        if (eq1.getRight() instanceof Apostrophe) {
                            Apostrophe a2 = (Apostrophe) eq1.getRight();
                            if (eq2.getRight() instanceof Variable && eq2.getLeft() instanceof Variable) {
                                return eq2.getRight().equals(a2.getExpr()) && eq2.getLeft().equals(a1.getExpr());
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkA4(Expression e) {
        if (e instanceof Negation) {
            Negation neg = (Negation) e;
            if (neg.getExpr() instanceof Equals) {
                Equals eq = (Equals) neg.getExpr();
                if (eq.getLeft() instanceof Apostrophe) {
                    Apostrophe ap = (Apostrophe) eq.getLeft();
                    return (eq.getRight() instanceof Zero && ap.getExpr() instanceof Variable);
                }
            }
        }
        return false;
    }

    private static boolean checkA5(Expression e) {
        if (e instanceof Equals) {
            Equals eq = (Equals) e;
            if (eq.getLeft() instanceof Plus && eq.getRight() instanceof Apostrophe) {
                Plus p1 = (Plus) eq.getLeft();
                Apostrophe ap1 = (Apostrophe) eq.getRight();
                if (ap1.getExpr() instanceof Plus) {
                    Plus p2 = (Plus) ap1.getExpr();
                    if (p1.getLeft() instanceof Variable && p1.getRight() instanceof Apostrophe) {
                        Apostrophe ap2 = (Apostrophe) p1.getRight();
                        if (ap2.getExpr() instanceof Variable) {
                            return ap2.getExpr().equals(p2.getRight()) && p1.getLeft().equals(p2.getLeft());
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkA6(Expression e) {
        if (e instanceof Equals) {
            Equals eq = (Equals) e;
            if (eq.getRight() instanceof Variable && eq.getLeft() instanceof Plus) {
                Plus p = (Plus) eq.getLeft();
                return p.getRight() instanceof Zero && p.getLeft().equals(eq.getRight());
            }
        }
        return false;
    }

    private static boolean checkA7(Expression e) {
        if (e instanceof Equals) {
            Equals eq = (Equals) e;
            if (eq.getRight() instanceof Zero && eq.getLeft() instanceof Times) {
                Times p = (Times) eq.getLeft();
                return p.getRight() instanceof Zero && p.getLeft() instanceof Variable;
            }
        }
        return false;
    }

    private static boolean checkA8(Expression e) {
        if (e instanceof Equals) {
            Equals eq = (Equals) e;
            if (eq.getLeft() instanceof Times && eq.getRight() instanceof Plus) {
                Times t1 = (Times) eq.getLeft();
                Plus p = (Plus) eq.getRight();
                if (t1.getLeft() instanceof Variable && t1.getRight() instanceof Apostrophe) {
                    Apostrophe ap = (Apostrophe) t1.getRight();
                    if (ap.getExpr() instanceof Variable && p.getLeft() instanceof Times) {
                        Times t2 = (Times) p.getLeft();
                        return t1.getLeft().equals(p.getRight()) && t1.getLeft().equals(t2.getLeft()) &&
                                t1.getRight().equals(t2.getRight());
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkA9(Expression e) {
        if (e instanceof Implication) {
            Implication impl1 = (Implication) e;
            if (impl1.getLeft() instanceof Conjunction) {
                Conjunction conj = (Conjunction) impl1.getLeft();
                if (conj.getRight() instanceof ForAll) {
                    ForAll forAll = (ForAll) conj.getRight();
                    Variable x = forAll.getVar();
                    if (forAll.getExpr() instanceof Implication) {
                        Implication impl2 = (Implication) forAll.getExpr();
                        Expression ee = change(conj.getLeft(), new Zero(), x);
                        boolean fl1 = conj.getLeft().equals(change(impl1.getRight(), new Zero(), x));
                        boolean fl2 = impl1.getRight().equals(impl2.getLeft());
                        boolean fl3 = impl2.getRight().equals(change(impl1.getRight(), new Apostrophe(x), x));
                        return fl1 && fl2 && fl3;
                    }
                }
            }
        }
        return false;
    }

    public static int checker(Expression e) {
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
        if (checkA1(e)) return 101;
        if (checkA2(e)) return 102;
        if (checkA3(e)) return 103;
        if (checkA4(e)) return 104;
        if (checkA5(e)) return 105;
        if (checkA6(e)) return 106;
        if (checkA7(e)) return 107;
        if (checkA8(e)) return 108;
        if (checkA9(e)) return 13;
        return -1;
    }
}
