package sci.config.compilator.map;

import sci.config.compilator.object.Lex_Node;

import java.util.ArrayList;

public class Lex_map {

    private static Lex_map instance;

    private ArrayList<Lex_Node> lex_map;

    private Lex_map(){
        lex_map = new ArrayList<>();
    }

    private static void init(){
        if (instance == null)
            instance = new Lex_map();
    }

    public static Lex_map use(){
        init();
        return instance;
    }

    public static Lex_map close(){
        return instance = null;
    }

    public void add(Lex_Node node){
        lex_map.add(node);
    }

    public Lex_Node[] get(){
        return (Lex_Node[]) lex_map.toArray();
    }

    public Lex_Node out(){
        Lex_Node node = lex_map.get(0);
        lex_map.remove(0);
        return node;
    }

    public boolean notEmpty(){
        return !lex_map.isEmpty();
    }

    public void reset(){
        lex_map.clear();
    }
}
