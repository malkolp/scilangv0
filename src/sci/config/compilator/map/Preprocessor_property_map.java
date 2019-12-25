package sci.config.compilator.map;

import sci.config.preprocessor.Preprocessor_cache;
import sci.config.compilator.object.Preprocessor_property;
import sci.config.preprocessor.support.Preprocessor_type;

import java.util.HashMap;

public class Preprocessor_property_map {

    private static Preprocessor_property_map instance;

    private HashMap<String,Preprocessor_property> preprocessor_property_map;

    private String importKey;

    private Preprocessor_property_map(){
        preprocessor_property_map = new HashMap<>();
        importKey = "";
    }

    private static void init(){
        if (instance == null)
            instance = new Preprocessor_property_map();
    }

    public static Preprocessor_property_map use(){
        init();
        return instance;
    }

    public static Preprocessor_property_map close(){
        return instance = null;
    }

    public boolean add(String key, Preprocessor_property property){
        Preprocessor_type pt = Preprocessor_type.use();
        if (preprocessor_property_map.containsKey(key))
            return false;
        if (property.getPreprocessor_type() == pt.importer()){
            if (!importKey.equals(""))
                preprocessor_property_map.remove(importKey);
            this.importKey = key;
        }
        preprocessor_property_map.put(key,property);
        Preprocessor_cache.use().add(property);
        Preprocessor_cache.use().add_preprocessor_key(key);
        Preprocessor_type.close();
        return true;
    }

    public Preprocessor_property get(String key){
        return preprocessor_property_map.get(key);
    }

    public boolean contains(String key){
        return preprocessor_property_map.containsKey(key);
    }

    public String importKey(){
        return importKey;
    }
}
