package sci.config.compilator.map;

import sci.config.preprocessor.Preprocessor_cache;
import sci.config.compilator.object.Non_terminal_property;
import sci.config.preprocessor.support.Non_terminal_actor;

import java.util.HashMap;

public class Non_terminal_property_map {

    private static Non_terminal_property_map instance;

    private HashMap<String, Non_terminal_property> non_terminal_property_map;
    private String idKey;
    private String constantKey;

    private Non_terminal_property_map(){
        non_terminal_property_map = new HashMap<>();
        idKey = "";
        constantKey = "";
    }

    private static void init(){
        if (instance == null)
            instance = new Non_terminal_property_map();
    }

    public static Non_terminal_property_map use(){
        init();
        return instance;
    }

    public static Non_terminal_property_map close(){
        return instance = null;
    }

    public boolean add(String key, Non_terminal_property property){
        Non_terminal_actor nta = Non_terminal_actor.use();
        if (non_terminal_property_map.containsKey(key))
            return false;
        if (property.getAct_as() == nta.IDENTIFIER()){
            if (!idKey.equals(""))
                non_terminal_property_map.remove(idKey);
            this.idKey = key;
        } else if (property.getAct_as() == nta.CONSTANT()){
            if (!constantKey.equals(""))
                non_terminal_property_map.remove(constantKey);
            this.constantKey = key;
        }
        non_terminal_property_map.put(key, property);
        Preprocessor_cache.use().add(property);
        Preprocessor_cache.use().add_non_terminal_key(key);
        Non_terminal_actor.close();
        return true;
    }

    public boolean contains(String key){
        return non_terminal_property_map.containsKey(key);
    }

    public String idKey(){
        return idKey;
    }

    public String constantKey(){
        return constantKey;
    }

    public Non_terminal_property get(String key){
        return non_terminal_property_map.get(key);
    }
}
