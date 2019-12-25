package sci.compiler.register;

import sci.config.compilator.map.*;
import sci.config.compilator.object.Identifier;
import sci.config.compilator.object.Lex_Node;
import sci.config.compilator.object.Value;
import sci.config.compilator.regex.Regex;
import sci.config.preprocessor.support.Datatype;

public class Register {

    private static Register instance;

    private Lex_map lex_map;
    private Identifier_map identifier_map;
    private Value_map value_map;
    private Constant_map constant_map;

    private Register(){
        identifier_map = Identifier_map.use();
        constant_map   = Constant_map.use();
        value_map      = Value_map.use();
        lex_map        = Lex_map.use();
    }

    private static void init(){
        if (instance == null)
            instance = new Register();
    }

    public static Register use(){
        init();
        return instance;
    }

    public static Register close(){
        return instance = null;
    }

    public void register_id(Lex_Node node){
        identifier_map.add(node,new Identifier());
        lex_map.add(node);
    }

    public void register_constant(Lex_Node node, int datatype,String value){
        Identifier identifier = new Identifier();
        identifier.setData_type(datatype);
        Value value_constant = new Value(value);
        value_constant.setData_type(datatype);
        constant_map.add(node,identifier);
        value_map.add(identifier,value_constant);
        lex_map.add(node);
    }

    public void register(Lex_Node node){
        Non_terminal_property_map ntpm = Non_terminal_property_map.use();
        Datatype dt = Datatype.use();
        Regex regex = Regex.use();
        String key = node.getToken();
        if (regex.alphanum(key) || regex.numConstant(key)){
            if (ntpm.contains(key)){
                lex_map.add(node);
            } else {
                if (regex.numConstant(key)){
                    if (regex.doubleType(key)){
                        register_constant(node,dt.DOUBLE(),key);
                    } else if (regex.floatType(key)){
                        register_constant(node,dt.FLOAT(),key);
                    } else {
                        register_constant(node,dt.INTEGER(),key);
                    }
                } else if (regex.identifier(key)){
                    if (key.equals("true") || key.equals("false")){
                        register_constant(node,dt.BOOLEAN(),key);
                    } else {
                        register_id(node);
                    }
                } else {
                    System.out.println("CALL ERROR");
                }
            }
        } else {
            if (regex.numConstant(key)){
                if (regex.doubleType(key)){
                    register_constant(node,dt.DOUBLE(),key);
                } else {
                    register_constant(node,dt.FLOAT(),key);
                }
            } else {
                register_symbol(node,key);
            }
        }
    }

    private void register_symbol(Lex_Node node, String key){
        Non_terminal_property_map ntpm = Non_terminal_property_map.use();
        if (ntpm.contains(key)){
            lex_map.add(node);
        } else {
            if (key.length() > 1){
                StringBuilder key1 = new StringBuilder();
                String key2;
                if (key.length() > 2){
                    for (int i = 0; i < key.length()-1;i++){
                        key1.append(key.charAt(i));
                    }
                } else
                    key1.append(key.charAt(0));
                key2 = key.charAt(key.length()-1)+"";
                register_symbol(new Lex_Node(key1.toString()),key1.toString());
                register_symbol(new Lex_Node(key2),key2);
            } else {
                System.out.println("CALL ERROR HANDLER");
            }
        }
    }

    public void clear_constant(){
        constant_map.clear();
    }

    public void setFixedDatatype(Lex_Node node){
        identifier_map.get(node).setFixed();
    }

    public void setValue(Lex_Node node,String value){
        Identifier identifier = identifier_map.get(node);
        value_map.get(identifier).setValue(value);
    }

}
