import java.util.Map;

public class Zero implements Expression {

    public Zero() {}

    @Override
    public boolean evaluate(Map<String, Boolean> map) {
        return false;
    }

    @Override
    public String toString() {
        return "0";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Zero);
    }
}
