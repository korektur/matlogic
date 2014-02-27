import java.util.ArrayList;

public class ExcludedMiddle {

    public static ArrayList<Expression> contrPositionRule(Expression alpha, Expression beta) {
        ArrayList<Expression> proof = new ArrayList<>();
        ArrayList<Expression> g = new ArrayList<>();
        g.add(new Implication(alpha, beta));
        proof.add(new Implication(new Implication(alpha, beta), new Implication(
                new Implication(alpha, new Negation(beta)), new Negation(alpha))));
        proof.add(g.get(0));
        proof.add(new Implication(new Implication(alpha, new Negation(beta)), new Negation(alpha)));
        proof.add(new Implication(new Negation(beta), new Implication(alpha, new Negation(beta))));
        proof.add(new Negation(beta));
        proof.add(new Implication(alpha, new Negation(beta)));
        proof.add(new Negation(alpha));
        proof = (new Deduction(new Negation(beta), new Negation(alpha), g, proof)).getProof();
        proof = (new Deduction(g.get(0), new Implication(new Negation(beta),
                new Negation(alpha)), new ArrayList<Expression>(), proof)).getProof();
        return proof;
    }

    public static ArrayList<Expression> getProof(Expression alpha) {
        ArrayList<Expression> proof = new ArrayList<>();
        proof.add(new Implication(alpha, new Disjunction(alpha, alpha)));
        proof.addAll(contrPositionRule(alpha, new Disjunction(alpha, new Negation(alpha))));
        proof.add(new Implication(new Negation(new Disjunction(alpha, new Negation(alpha))), new Negation(alpha)));
        proof.add(new Implication(new Negation(alpha), new Disjunction(alpha, new Negation(alpha))));
        proof.addAll(contrPositionRule(new Negation(alpha), new Disjunction(alpha, new Negation(alpha))));
        proof.add(new Implication(new Negation(new Disjunction(alpha, new Negation(alpha))), new Negation(new Negation(alpha))));
        proof.add(new Implication(new Implication(new Negation(new Disjunction(alpha, new Negation(alpha))),
                new Negation(alpha)), new Implication(new Implication(new Negation(new Disjunction(alpha,
                new Negation(alpha))), new Negation(new Negation(alpha))), new Negation(new Negation(
                new Disjunction(alpha, new Negation(alpha)))))));
        proof.add(new Implication(new Implication(new Negation(new Disjunction(alpha, new Negation(alpha))),
                new Negation(new Negation(alpha))), new Negation(new Negation(new Disjunction(alpha, new Negation(alpha))))));
        proof.add(new Negation(new Negation(new Disjunction(alpha, new Negation(alpha)))));
        proof.add(new Implication(new Negation(new Negation(new Disjunction(alpha, new Negation(alpha)))),
                new Disjunction(alpha, new Negation(alpha))));
        proof.add(new Disjunction(alpha, new Negation(alpha)));
        return proof;
    }
}
