package sci.config.compilator.map;

import sci.config.compilator.object.Identifier;
import sci.config.compilator.object.Lex_Node;

import java.util.HashMap;

public class Identifier_map {

    private static Identifier_map instance;

    private HashMap<Lex_Node, Identifier> identifier_map;

    private Identifier_map(){
        identifier_map = new HashMap<>();
    }

    private static void init(){
        if (instance == null)
            instance = new Identifier_map();
    }

    public static Identifier_map use(){
        init();
        return instance;
    }

    public static Identifier_map close(){
        return instance = null;
    }

    public boolean add(Lex_Node node, Identifier identifier){
        if (identifier_map.containsKey(node))
            return false;
        identifier_map.put(node,identifier);
        return true;
    }

    public Identifier get(Lex_Node node){
        return identifier_map.get(node);
    }
}
