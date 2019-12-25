package sci.config.compilator.object;

public class Non_terminal_property {

    private int precedence,act_as;

    public Non_terminal_property(int precedence,int act_as){
        this.precedence = precedence;
        this.act_as = act_as;
    }

    public int getPrecedence() {
        return precedence;
    }

    public int getAct_as() {
        return act_as;
    }
}
