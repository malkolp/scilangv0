package sci.config.compilator.map;

import sci.config.compilator.object.Identifier;
import sci.config.compilator.object.Lex_Node;
import sci.config.compilator.object.Value;

import java.util.HashMap;

public class Constant_map {

    private static Constant_map instance;

    private HashMap<Lex_Node, Identifier> constant_map;

    private Constant_map(){
        constant_map = new HashMap<>();
    }

    private static void init(){
        if (instance == null)
            instance = new Constant_map();
    }

    public static Constant_map use(){
        init();
        return instance;
    }

    public static Constant_map close(){
        return instance = null;
    }

    public void add(Lex_Node node, Identifier identifier){
        constant_map.put(node, identifier);
    }

    public void clear(){
        constant_map.clear();
    }


}
