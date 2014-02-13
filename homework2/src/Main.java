import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        String s = in.readLine();
        Parser parser;
        String[] arg = s.split(",");
        ArrayList<Expression> added = new ArrayList<Expression>();
        for (int i = 0; i < arg.length - 1; i++) {
            parser = new Parser(arg[i]);
            added.add(parser.parse());
        }
        String[] todo = arg[arg.length - 1].split("\\|-");

        Expression alpha = (new Parser(todo[0])).parse();
        Expression beta = (new Parser(todo[1])).parse();
        ArrayList<Expression> ans = new ArrayList<Expression>();
        s = in.readLine();
        while (s != null) {
            parser = new Parser(s);
            expressions.add(parser.parse());
            s = in.readLine();
        }
        boolean isgood = true;
        int i = 0;
        for (Expression expr : expressions) {
            i++;
            isgood = false;
            if (Axioms.checker(expr) != -1) {
                ans.add(expr);
                ans.add(new Implication(expr, new Implication(alpha, expr)));
                ans.add(new Implication(alpha, expr));
                isgood = true;
                continue;
            }
            for (Expression g : added) {
                if (g.equals(expr)) {
                    ans.add(expr);
                    ans.add(new Implication(expr, new Implication(alpha, expr)));
                    ans.add(new Implication(alpha, expr));
                    isgood = true;

                }
                if (isgood) break;
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
                isgood = true;
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
                                isgood = true;
                                break;
                            }
                        }
                    }
                    if (isgood)
                        break;
                }
            }
        }
        if (isgood) {
            for (Expression expr : ans) {
                out.println(expr.toString());
            }



        } else {
            for (Expression expr : ans) {
                out.println(expr.toString());
            }
            out.println("Error");
        }
        out.close();
    }

}
