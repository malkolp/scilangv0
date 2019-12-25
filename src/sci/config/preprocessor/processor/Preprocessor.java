package sci.config.preprocessor.processor;

import sci.config.compilator.map.Preprocessor_property_map;
import sci.config.compilator.map.Token_map;
import sci.config.compilator.object.Preprocessor_property;
import sci.config.compilator.object.Token;
import sci.config.preprocessor.support.Token_type;

class Preprocessor {

    private static Preprocessor instance;

    private Preprocessor(){}

    private static void init(){
        if (instance == null)
            instance = new Preprocessor();
    }

    static Preprocessor use(){
        init();
        return instance;
    }

    static Preprocessor close(){
        return instance = null;
    }

    boolean add(String key, int preprocessor_type){
        Token token = new Token(Token_type.use().PREPROCESSOR());
        Preprocessor_property property = new Preprocessor_property(preprocessor_type);
        if (Token_map.use().add(key,token))
            return Preprocessor_property_map.use().add(key, property);
        return false;
    }

    void load(String key, int preprocessor_type){
        if (Token_map.use().contains(key))
            if (!Preprocessor_property_map.use().contains(key)) {
                Preprocessor_property_map.use().add(key, new Preprocessor_property(preprocessor_type));
            }
    }
}
