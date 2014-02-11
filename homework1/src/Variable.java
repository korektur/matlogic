import java.util.Map;


public class Variable implements Expression{

    private String name;

    public Variable(String name){
        this.name = name;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var){
        return var.get(name);
    }
}
