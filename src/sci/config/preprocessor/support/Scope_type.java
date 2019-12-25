package sci.config.preprocessor.support;

public class Scope_type {

    private static Scope_type instance;

    private Scope_type(){}

    private static void init(){
        if (instance == null)
            instance = new Scope_type();
    }

    public static Scope_type use(){
        init();
        return instance;
    }

    public static Scope_type close(){
        return instance = null;
    }

    public int GLOBAL(){return 1;}

    public int FILE(){return 2;}

    public int CLASS(){return 3;}

    public int LOCAL(){return 4;}

    public int NO_SCOPE(){return 0;}
}
