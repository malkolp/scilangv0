package sci.config.preprocessor.processor;

import sci.config.compilator.map.Non_terminal_property_map;
import sci.config.compilator.map.Token_map;
import sci.config.compilator.object.Non_terminal_property;
import sci.config.compilator.object.Token;
import sci.config.preprocessor.support.Token_type;

class Non_terminal {

    private static Non_terminal instance;

    private Non_terminal(){}

    private static void init(){
        if (instance == null)
            instance = new Non_terminal();
    }

    static Non_terminal use(){
        init();
        return instance;
    }

    static Non_terminal close(){
        return instance = null;
    }

    boolean add(String key,int precedence,int act_as){
        Token token = new Token(Token_type.use().NON_TERMINAL());
        Non_terminal_property property = new Non_terminal_property(precedence, act_as);
        if (Token_map.use().add(key,token))
            return Non_terminal_property_map.use().add(key,property);
        return false;
    }

    void load(String key, int precedence, int act_as){
        if (Token_map.use().contains(key))
            if (!Non_terminal_property_map.use().contains(key)) {
                Non_terminal_property_map.use().add(key, new Non_terminal_property(precedence, act_as));
            }
    }
}
