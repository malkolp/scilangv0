package sci.compiler.lexical;

import sci.compiler.register.Register;
import sci.config.compilator.map.Lex_map;
import sci.config.compilator.map.Non_terminal_property_map;
import sci.config.compilator.object.Lex_Node;
import sci.config.compilator.object.Non_terminal_property;
import sci.config.compilator.regex.Regex;
import sci.config.preprocessor.support.Datatype;

public class Lexer {

    private static Lexer instance;

    private Lexer(){}

    private static void init(){
        if (instance == null)
            instance = new Lexer();
    }

    public static Lexer use(){
        init();
        return instance;
    }

    public static Lexer close(){
        return instance = null;
    }

    public Lex_Node[] lex(String raw_code){
        return process(raw_code);
    }

    private Lex_Node[] process(String code){
        Non_terminal_property_map ntpm = Non_terminal_property_map.use();
        Register register = Register.use();
        Regex regex = Regex.use();
        Datatype datatype = Datatype.use();

        boolean string_token = false;
        char point;
        String key = "";
        int iter = 0;

        while (iter < code.length()){
            point = code.charAt(iter);
            if (string_token){
                if (regex.quoteMarks(point) && !regex.escaped(key)){
                    Lex_Node node = construct_node(ntpm.constantKey());
                    register.register_constant(node,datatype.STRING(),key);
                    key = point+"";
                    string_token = false;
                } else {
                    key = key + point;
                }
            } else {
                if (regex.isSpace(point)){
                    if (regex.isNotEmpty(key)){
                        register.register(construct_node(key));
                        key = "";
                    }
                } else {
                    if (regex.alphanum(point)){
                        if (regex.alphanum(key)){
                            key = key + point;
                        } else {
                            if (regex.decimal(key) && regex.is_F_or_D(point)){
                                key = key + point;
                            } else if (regex.lastDot(key) && regex.numeric(point)){
                                key = key + point;
                            } else {
                                register.register(construct_node(key));
                                key = point+"";
                            }
                        }
                    } else {
                        if (regex.quoteMarks(point) && !regex.escaped(key)){
                            if (regex.isNotEmpty(key)){
                                register.register(construct_node(key));
                            }
                            key = point+"";
                            register.register(construct_node(key));
                            key = "";
                            string_token = true;
                        } else {
                            if (regex.alphanum(key)){
                                if (regex.numeric(key) && regex.isDot(point)){
                                    key = key + point;
                                } else {
                                    if (regex.isNotEmpty(key)){
                                        register.register(construct_node(key));
                                    }
                                    key = point+"";
                                }
                            } else {
                                if (regex.numConstant(key)){
                                    register.register(construct_node(key));
                                    key = point + "";
                                } else {
                                    key = key + point;
                                }
                            }
                        }
                    }
                }
            }
            iter++;
        }
        if (regex.isNotEmpty(key)){
            register.register(construct_node(key));
        }

        return Lex_map.use().get();
    };

    private Lex_Node construct_node(String key){
        return new Lex_Node(key);
    }
}
