package sci.config.preprocessor.support;

public class Non_terminal_actor {

    private static Non_terminal_actor instance;

    private Non_terminal_actor(){}

    private static void init(){
        if (instance == null)
            instance = new Non_terminal_actor();
    }

    public static Non_terminal_actor use(){
        init();
        return instance;
    }

    public static Non_terminal_actor close(){
        return instance = null;
    }

    public int IDENTIFIER(){ return 1; }

    public int INPUT(){ return 2; }

    public int OUTPUT(){ return 3; }

    public int OPERATOR(){ return 4; }

    public int RETURN(){ return 5; }

    public int CONSTANT(){ return 6; }

    public int EMPTY(){return 9;}

}
