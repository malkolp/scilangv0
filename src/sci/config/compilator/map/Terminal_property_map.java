package sci.config.compilator.map;

import sci.config.preprocessor.Preprocessor_cache;
import sci.config.compilator.object.Terminal_property;
import sci.config.compilator.object.Token;

import java.util.HashMap;

public class Terminal_property_map {

    private static Terminal_property_map instance;

    private HashMap<String, Terminal_property> terminal_property_map;
    private HashMap<String, String> grammar_map;

    private Terminal_property_map(){
        terminal_property_map = new HashMap<>();
        grammar_map = new HashMap<>();
    }

    private static void init(){
        if (instance == null)
            instance = new Terminal_property_map();
    }

    public static Terminal_property_map use(){
        init();
        return instance;
    }

    public static Terminal_property_map close(){
        return instance = null;
    }

    public boolean add(String key,Terminal_property property){
        if (terminal_property_map.containsKey(key)){
            terminal_property_map.get(key).addChild(property.getChild());
        } else {
            terminal_property_map.put(key, property);
            Preprocessor_cache.use().add(property);
            Preprocessor_cache.use().add_terminal_key(key);
        }
        grammar_map.put(property.getChild(),key);
        return true;
    }

    public void add_grammar_child(String key, String child){
        if (!contains_sentence(key))
            grammar_map.put(child,key);
    }

    public Terminal_property get(String key){
        return terminal_property_map.get(key);
    }

    public String getTerminal(String key){ return grammar_map.get(key); }

    public boolean contains_sentence(String key){
        return grammar_map.containsKey(key);
    }
}
