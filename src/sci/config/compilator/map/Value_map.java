package sci.config.compilator.map;

import sci.config.compilator.object.Identifier;
import sci.config.compilator.object.Value;

import java.util.HashMap;

public class Value_map {

    private static Value_map instance;

    private HashMap<Identifier, Value> value_map;

    private Value_map(){
        value_map = new HashMap<>();
    }

    private static void init(){
        if (instance == null)
            instance = new Value_map();
    }

    public static Value_map use(){
        init();
        return instance;
    }

    public static Value_map close(){
        return instance = null;
    }

    public boolean add(Identifier identifier, Value value){
        if (value_map.containsKey(identifier))
            return false;
        value_map.put(identifier, value);
        return true;
    }

    public Value get(Identifier identifier){
        return value_map.get(identifier);
    }
}
