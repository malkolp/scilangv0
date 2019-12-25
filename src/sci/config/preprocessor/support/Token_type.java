package sci.config.preprocessor.support;

public class Token_type {

    private static Token_type instance;

    private Token_type(){
    }

    private static void init(){
        if (instance == null)
            instance = new Token_type();
    }

    public static Token_type use(){
        init();
        return instance;
    }

    public static Token_type close(){
        return instance = null;
    }

    public int PREPROCESSOR(){
        return 1;
    }

    public int NON_TERMINAL(){
        return 2;
    }

    public int TERMINAL(){
        return 3;
    }

}
