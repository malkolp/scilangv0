package sci.config.preprocessor.processor;

import sci.config.compilator.map.Terminal_property_map;
import sci.config.compilator.map.Token_map;
import sci.config.compilator.object.Terminal_property;
import sci.config.compilator.object.Token;
import sci.config.preprocessor.support.Token_type;

class Terminal {

    private static Terminal instance;

    private Terminal(){}

    private static void init(){
        if (instance == null)
            instance = new Terminal();
    }

    static Terminal use(){
        init();
        return instance;
    }

    static Terminal close(){
        return instance = null;
    }

    boolean add(String key, String child, int precedence){
        if (checkChild(child))
            return false;
        Token token = new Token(Token_type.use().TERMINAL());
        Terminal_property property = new Terminal_property(precedence, child);
        if (Token_map.use().add(key,token))
            if (!Terminal_property_map.use().contains_sentence(child))
                return Terminal_property_map.use().add(key,property);
        return false;
    }

    boolean add(String key, String child){
        if (checkChild(child))
            return false;
        if (Token_map.use().contains(key)){
            String processed_child = child.replace(" ","<AND>");
            if (Terminal_property_map.use().contains_sentence(child))
                return false;
            Terminal_property_map.use().get(key).addChild(processed_child);
            Terminal_property_map.use().add_grammar_child(key, child);
            return true;
        }
        return false;
    }

    void load(String key, String child, int precedence){
        Terminal_property_map.use().add(key,new Terminal_property(precedence, child));
        String[] grammars = child.split("<OR>");
        for (String str:grammars){
            str = str.replace("<AND>"," ");
            Terminal_property_map.use().add_grammar_child(key, str);
        }
    }

    private boolean checkChild(String child){
        String[] children = child.split(" ");
        for (String i:children)
            if (!Token_map.use().contains(i))
                return true;
        return false;
    }
}
