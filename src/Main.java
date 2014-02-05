import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Руслан on 05.02.14.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        out.close();
    }

}
