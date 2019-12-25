package sci.config.preprocessor.support;

public class Precedence {

    private static Precedence instance;

    private Precedence(){}

    private static void init(){
        if (instance == null)
            instance = new Precedence();
    }

    public static Precedence use(){
        init();
        return instance;
    }

    public static Precedence close(){
        return instance = null;
    }

    public int HIGHEST(){ return 10; }

    public int HIGHER(){ return 8; }

    public int HIGH(){ return 6; }

    public int MEDIUM(){ return 5; }

    public int LOW(){ return 4; }

    public int LOWER(){ return 2; }

    public int LOWEST(){ return 0; }


}
