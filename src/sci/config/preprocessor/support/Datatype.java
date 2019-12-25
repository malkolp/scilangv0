package sci.config.preprocessor.support;

public class Datatype {

    private static Datatype instance;

    private Datatype(){}

    private static void init(){
        if (instance == null)
            instance = new Datatype();
    }

    public static Datatype use(){
        init();
        return instance;
    }

    public static Datatype close(){
        return instance = null;
    }

    public int INTEGER(){ return 1; }

    public int DOUBLE(){ return 2; }

    public int FLOAT(){ return 3; }

    public int CHAR(){ return 4; }

    public int BOOLEAN(){ return 5; }

    public int STRING(){ return 6; }
}
