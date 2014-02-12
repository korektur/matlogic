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
        while(s != null) {
            parser = new Parser(s);
            expressions.add(parser.parse());
            s = in.readLine();
        }
        for(int i = 0; i < expressions.size(); i++){
            out.println(Axioms.checker(expressions.get(i)));
        }
        out.close();
    }

}
