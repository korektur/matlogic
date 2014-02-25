import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static Expression expr;
    private static ArrayList<String> vars;
    private static PrintWriter out;
    private static ArrayList<Expression> ans;
    private static ArrayList<ArrayList<Expression>> linkings;

    private static class Pair {
        Map<String, Boolean> map;
        ArrayList<Expression> proof;

        public Pair(Map<String, Boolean> map, ArrayList<Expression> proof) {
            this.map = map;
            this.proof = proof;
        }
    }

    private static boolean checkExpr(int index, Map<String, Boolean> map) {
        if (index == vars.size()) {
            boolean fl = expr.evaluate(map);
            if (!fl) {
                out.print("Высказывание ложно при ");
                for (String var : vars) {
                    out.print(var + "=");
                    if (map.get(var)) {
                        out.print("И");
                    } else {
                        out.print("Л");
                    }
                    out.print(", ");
                }
            }
            return fl;
        }
        map.put(vars.get(index), true);
        boolean fl1 = checkExpr(index + 1, map);
        map.put(vars.get(index), false);
        boolean fl2 = checkExpr(index + 1, map);
        return fl1 && fl2;
    }

    private static boolean checkExpr() {
        return checkExpr(0, new TreeMap<String, Boolean>());
    }

    private static Expression replace(Expression ex, Expression a, Expression b) {
        if (ex instanceof Implication) {
            Implication impl = (Implication) ex;
            return new Implication(replace(impl.getLeft(), a, b), replace(impl.getRight(), a, b));
        }
        if (ex instanceof Conjunction) {
            Conjunction conj = (Conjunction) ex;
            return new Conjunction(replace(conj.getLeft(), a, b), replace(conj.getRight(), a, b));
        }
        if (ex instanceof Disjunction) {
            Disjunction disj = (Disjunction) ex;
            return new Disjunction(replace(disj.getLeft(), a, b), replace(disj.getRight(), a, b));
        }
        if (ex instanceof Negation) {
            Negation neg = (Negation) ex;
            return new Negation(replace(neg.getExpr(), a, b));
        }
        if (ex instanceof Variable) {
            Variable v = (Variable) ex;
            if (v.toString().equals("A")) {
                return a;
            } else {
                return b;
            }
        }
        return null;
    }


    private static boolean rec(Map<String, Boolean> map, Expression expr) {
        if (expr instanceof Variable) {
            return expr.evaluate(map);
        }
        if (expr instanceof Implication) {
            Implication impl = (Implication) expr;
            boolean fl1 = rec(map, impl.getLeft());
            boolean fl2 = rec(map, impl.getRight());
            if (fl1 && fl2) {
                ArrayList<Expression> proof = linkings.get(4);
                for (Expression ex : proof) {
                    ans.add(replace(ex, impl.getLeft(), impl.getRight()));
                }
                return true;
            } else if (fl1) {
                ArrayList<Expression> proof = linkings.get(5);
                for (Expression ex : proof) {
                    ans.add(replace(ex, impl.getLeft(), impl.getRight()));
                }
                return false;
            } else if (fl2) {
                ArrayList<Expression> proof = linkings.get(6);
                for (Expression ex : proof) {
                    ans.add(replace(ex, impl.getLeft(), impl.getRight()));
                }
                return true;
            } else {
                ArrayList<Expression> proof = linkings.get(7);
                for (Expression ex : proof) {
                    ans.add(replace(ex, impl.getLeft(), impl.getRight()));
                }
                return true;
            }
        }
        if (expr instanceof Conjunction) {
            Conjunction conj = (Conjunction) expr;
            boolean fl1 = rec(map, conj.getLeft());
            boolean fl2 = rec(map, conj.getRight());
            if (fl1 && fl2) {
                ArrayList<Expression> proof = linkings.get(0);
                for(Expression ex : proof) {
                    ans.add(replace(ex, conj.getLeft(), conj.getRight()));
                }
                return true;
            } else if (fl1) {
                ArrayList<Expression> proof = linkings.get(1);
                for(Expression ex : proof) {
                    ans.add(replace(ex, conj.getLeft(), conj.getRight()));
                }
                return false;
            } else if (fl2) {
                ArrayList<Expression> proof = linkings.get(2);
                for(Expression ex : proof) {
                    ans.add(replace(ex, conj.getLeft(), conj.getRight()));
                }
                return false;
            } else {
                ArrayList<Expression> proof = linkings.get(3);
                for(Expression ex : proof) {
                    ans.add(replace(ex, conj.getLeft(), conj.getRight()));
                }
                return false;
            }
        }
        if (expr instanceof Disjunction) {
            Disjunction disj = (Disjunction) expr;
            boolean fl1 = rec(map, disj.getLeft());
            boolean fl2 = rec(map, disj.getRight());
            if (fl1 && fl2) {
                ArrayList<Expression> proof = linkings.get(8);
                for(Expression ex : proof) {
                    ans.add(replace(ex, disj.getLeft(), disj.getRight()));
                }
                return true;
            } else if (fl1) {
                ArrayList<Expression> proof = linkings.get(9);
                for(Expression ex : proof) {
                    ans.add(replace(ex, disj.getLeft(), disj.getRight()));
                }
                return true;
            } else if (fl2) {
                ArrayList<Expression> proof = linkings.get(10);
                for(Expression ex : proof) {
                    ans.add(replace(ex, disj.getLeft(), disj.getRight()));
                }
                return true;
            } else {
                ArrayList<Expression> proof = linkings.get(11);
                for(Expression ex : proof) {
                    ans.add(replace(ex, disj.getLeft(), disj.getRight()));
                }
                return false;
            }
        }
        if (expr instanceof Negation) {
            Negation neg = (Negation) expr;
            boolean fl = rec(map, neg.getExpr());
            if (!fl) {
                ArrayList<Expression> proof = linkings.get(12);
                for(Expression ex : proof) {
                    ans.add(replace(ex, neg.getExpr(), null));
                }
                return false;
            } else {
                ArrayList<Expression> proof = linkings.get(13);
                for(Expression ex : proof) {
                    ans.add(replace(ex, neg.getExpr(), null));
                }
            }

        }
        return false;
    }

    private static void getProof(int index, Map<String, Boolean> map) {
        if (index == vars.size()) {
            rec(map, expr);
            return;
        }
        map.put(vars.get(index), true);
        getProof(index + 1, map);
        map.put(vars.get(index), false);
        getProof(index + 1, map);
    }


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        out = new PrintWriter("output.txt");
        linkings = new ArrayList<>();
        for (int i = 1; i <= 15; ++i) {
            BufferedReader input = new BufferedReader(new FileReader("expression" + i));
            linkings.add(new ArrayList<Expression>());
            String s = input.readLine();
            while (s != null) {
                linkings.get(i - 1).add((new Parser(s)).parse());
                s = in.readLine();
            }
        }
        String input = in.readLine();
        Parser parser = new Parser(input);
        ans = new ArrayList<>();
        expr = parser.parse();
        vars = parser.getVariables();
        boolean isCorrect = checkExpr();
        if (isCorrect) {
            getProof(0, new TreeMap<String, Boolean>());
        }
        out.close();
    }

}
