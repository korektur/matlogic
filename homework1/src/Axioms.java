

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
                if (impl2.getLeft() instanceof Implication) {
                    Implication impl3 = (Implication) impl2.getLeft();
                    if (impl2.getRight() instanceof Implication) {
                        Implication impl4 = (Implication) impl2.getRight();
                        if (impl4.getLeft() instanceof Implication) {
                            Implication impl5 = (Implication) impl4.getLeft();
                            if (impl1.getRight() instanceof Implication) {
                                Implication impl6 = (Implication) impl1.getRight();
                                boolean fl1 = impl3.getLeft().equals(impl5.getLeft()) && impl3.getLeft().equals(impl6.getLeft());
                                boolean fl2 = impl3.getRight().equals(impl5.getRight());
                                boolean fl3 = impl4.getRight().equals(impl6.getRight());
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
            if (impl1.getLeft() instanceof Implication) {
                Implication impl2 = (Implication) impl1.getLeft();
                if (impl1.getRight() instanceof Conjunction) {
                    Conjunction conj1 = (Conjunction) impl1.getRight();
                    boolean fl1 = impl2.getLeft().equals(conj1.getLeft());
                    boolean fl2 = impl2.getRight().equals(conj1.getRight());
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
}
