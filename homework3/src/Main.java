import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static Expression expr;
    private static ArrayList<String> vars;
    private static PrintWriter out;
    private static ArrayList<Expression> ans;

    private static boolean checkExpr(int index, Map<String, Boolean> map) {
        if (index == vars.size()) {
            boolean fl = expr.evaluate(map);
            if (!fl) {
                out.print("Высказывание ложно при ");
                for (String var : vars) {
                    out.print(var + "=");
                    if (map.get(var)){
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


    private static void rec(Map<String, Boolean> map, Expression expr) {
        if (expr instanceof BinaryOp) {
            BinaryOp ex = (BinaryOp) expr;
            if (ex.getLeft() instanceof BinaryOp)
                rec(map, ex);
        }
    }

    private static void getProof(int index, Map<String, Boolean> map) {
        if (index == vars.size()) {

        }
        map.put(vars.get(index), true);
        getProof(index + 1, map);
        map.put(vars.get(index), false);
        getProof(index + 1, map);
    }


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        out = new PrintWriter("output.txt");
        String input = in.readLine();
        Parser parser = new Parser(input);
        ans = new ArrayList<>();
        expr = parser.parse();
        vars = parser.getVariables();
        boolean isCorrect = checkExpr();

        out.close();
    }

}
