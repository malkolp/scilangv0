package sci.config.preprocessor.support;

public class Preprocessor_type {

    private static Preprocessor_type instance;

    private Preprocessor_type(){}

    private static void init(){
        if (instance == null)
            instance = new Preprocessor_type();
    }

    public static Preprocessor_type use(){
        init();
        return instance;
    }

    public static Preprocessor_type close(){
        return instance = null;
    }

    public int importer(){
        return 1;
    }

    public int special(){
        return 2;
    }
}
