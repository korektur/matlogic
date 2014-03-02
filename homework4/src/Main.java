import java.io.*;
import java.util.ArrayList;

public class Main {

    private static ArrayList<Expression> ans;
    private static Expression alpha;
    private static Expression beta;
    private static ArrayList<Expression> g;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        ArrayList<Expression> input = new ArrayList<>();
        String s = in.readLine();
        String[] arg = s.split(" ");
        g = new ArrayList<>();
        for(int i = 0; i < arg.length - 1; ++i) {
            g.add((new Parser(arg[i])).parse());
        }
        String[] todo = arg[arg.length].split("\\|-");
        alpha = (new Parser(todo[0])).parse();
        beta = (new Parser(todo[1])).parse();
        s = in.readLine();
        while(s != null) {
            input.add((new Parser(s)).parse());
            s = in.readLine();
        }
        ans = new ArrayList<>();
        boolean isGood = true;
        for (int i = 0; i < input.size() && isGood; ++i) {
            isGood = false;

        }
        for(Expression expr : ans) {
            out.println(ans);
        }
        out.close();
    }

}
