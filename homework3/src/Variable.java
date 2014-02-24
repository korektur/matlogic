import java.util.Map;


public class Variable implements Expression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    AOverride
    public boolean evaluate(Map<String, Boolean> var) {
        return var.get(name);
    }

    AOverride
    public boolean equals(Object o) {
        if (o instanceof Variable) {
            Variable v = (Variable) o;
            return v.name.equals(name);
        } else {
            return false;
        }
    }

    AOverride
    public String toString(){
        return name;
    }

}
