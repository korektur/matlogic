import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("C:/Users/Руслан/Documents/GitHub/matlogic/homework3/output.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        String s = in.readLine();
        Parser parser;
        while(s != null) {
            parser = new Parser(s);
            expressions.add(parser.parse());
            s = in.readLine();
        }
        boolean ans = true;
        int i = 0;
        for(; i < expressions.size() && ans; i++){
            //out.println(Axioms.checker(expressions.get(i)));
            ans = false;
            if(Axioms.checker(expressions.get(i)) != - 1){
                ans = true;
                continue;
            }
            for(int j = 0; j < i && !ans; ++j){
                if (expressions.get(j) instanceof Implication){
                    Implication impl = (Implication)expressions.get(j);
                    if (impl.getRight().equals(expressions.get(i))){
                        for(int k = 0; k < i && !ans; ++k){
                            if (expressions.get(k).equals(impl.getLeft())){
                                ans = true;

                            }
                        }
                    }
                }
            }
        }
        if (ans){
            out.println("Доказательство корректно.");
        } else {
            out.println("Доказательство некорректно начиная с " + i + " высказывания.");
        }
        out.close();
    }

}
