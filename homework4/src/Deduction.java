import java.util.ArrayList;


public class Deduction {
    private Expression alpha;
    private ArrayList<Expression> added;
    private ArrayList<Expression> expressions;

    public Deduction(Expression alpha, ArrayList<Expression> g, ArrayList<Expression> expressions) {
        this.alpha = alpha;
        this.added = g;
        this.expressions = expressions;
    }

    public ArrayList<Expression> getProof() {
        ArrayList<Expression> ans = new ArrayList<>();
        boolean isGood = true;
        int i = 0;
        for (Expression expr : expressions) {
            i++;
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

            for (int j = 0; j < i - 1; j++) {
                Expression mp = expressions.get(j);
                if (mp instanceof Implication) {
                    Implication impl = (Implication) mp;
                    if (impl.getRight().equals(expr)) {
                        for (int k = 0; k < i - 1; k++) {
                            Expression mp2 = expressions.get(k);
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
            if (!isGood)
                break;
        }
        if (isGood) {
            return ans;
        } else {
            throw null;
        }
    }
}
