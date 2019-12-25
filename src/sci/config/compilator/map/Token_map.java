package sci.config.compilator.map;

import sci.config.compilator.object.Non_terminal_property;
import sci.config.preprocessor.Preprocessor_cache;
import sci.config.compilator.object.Token;
import sci.config.preprocessor.support.Non_terminal_actor;
import sci.config.preprocessor.support.Precedence;
import sci.config.preprocessor.support.Token_type;

import java.util.HashMap;

public class Token_map {

    private static Token_map instance;

    private HashMap<String, Token> token_map;

    private Token_map(){
        token_map = new HashMap<>();
        token_map.put(delimeter_token(),new Token(Token_type.use().NON_TERMINAL()));
        Non_terminal_property_map.use().add(delimeter_token(),new Non_terminal_property(Precedence.use().LOWEST(), Non_terminal_actor.use().EMPTY()));
    }

    private static void init(){
        if (instance == null)
            instance = new Token_map();
    }

    public static Token_map use(){
        init();
        return instance;
    }

    public static Token_map close(){
        Constant_map.close();
        Identifier_map.close();
        Lex_map.close();
        Value_map.close();
        return instance = null;
    }

    public boolean add(String key,Token token){
        if (token_map.containsKey(key))
            return false;
        token_map.put(key, token);
        Preprocessor_cache.use().add(token);
        Preprocessor_cache.use().add_token_key(key);
        return true;
    }

    public Token get(String key){
        return token_map.get(key);
    }

    public boolean contains(String key){return token_map.containsKey(key);}

    public String delimeter_token(){
        return "_$DM";
    }
}
