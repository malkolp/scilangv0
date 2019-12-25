package sci.config.preprocessor;

import sci.config.compilator.object.Non_terminal_property;
import sci.config.compilator.object.Preprocessor_property;
import sci.config.compilator.object.Terminal_property;
import sci.config.compilator.object.Token;

import java.util.ArrayList;
public class Preprocessor_cache {

    private static Preprocessor_cache instance;

    private ArrayList<Token> token_cache;
    private ArrayList<Non_terminal_property> non_terminal_property_cache;
    private ArrayList<Terminal_property> terminal_property_cache;
    private ArrayList<Preprocessor_property> preprocessor_property_cache;
    private ArrayList<String> token_key_cache;
    private ArrayList<String> non_terminal_property_key_cache;
    private ArrayList<String> terminal_property_key_cache;
    private ArrayList<String> preprocessor_property_key_cache;

    private Preprocessor_cache(){
        token_cache = new ArrayList<>();
        non_terminal_property_cache = new ArrayList<>();
        terminal_property_cache = new ArrayList<>();
        preprocessor_property_cache = new ArrayList<>();
        token_key_cache = new ArrayList<>();
        non_terminal_property_key_cache = new ArrayList<>();
        terminal_property_key_cache = new ArrayList<>();
        preprocessor_property_key_cache = new ArrayList<>();
    }

    private static void init(){
        if (instance == null)
            instance = new Preprocessor_cache();
    }

    public static Preprocessor_cache use(){
        init();
        return instance;
    }

    public static Preprocessor_cache close(){
        return instance = null;
    }

    public void add(Token token){
        token_cache.add(token);
    }

    public void add(Non_terminal_property ntp){
        non_terminal_property_cache.add(ntp);
    }

    public void add(Terminal_property tp){
        terminal_property_cache.add(tp);
    }

    public void add(Preprocessor_property pp){
        preprocessor_property_cache.add(pp);
    }

    public void add_token_key(String key){token_key_cache.add(key);}

    public void add_non_terminal_key(String key){non_terminal_property_key_cache.add(key);}

    public void add_terminal_key(String key){terminal_property_key_cache.add(key);}

    public void add_preprocessor_key(String key){preprocessor_property_key_cache.add(key);}

    public ArrayList<Token> token_cache(){
        return token_cache;
    }

    public ArrayList<Non_terminal_property> non_terminal_property(){
        return non_terminal_property_cache;
    }

    public ArrayList<Terminal_property> terminal_property(){
        return terminal_property_cache;
    }

    public ArrayList<Preprocessor_property> preprocessor_property(){
        return preprocessor_property_cache;
    }

    public ArrayList<String> token_key_cache(){
        return token_key_cache;
    }

    public ArrayList<String> non_terminal_key_cache(){
        return non_terminal_property_key_cache;
    }

    public ArrayList<String> terminal_key_cache(){
        return terminal_property_key_cache;
    }

    public ArrayList<String> preprocessor_key_cache(){
        return preprocessor_property_key_cache;
    }
}
