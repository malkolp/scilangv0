package sci.compiler.syntactic;

import sci.config.compilator.map.Lex_map;
import sci.config.compilator.map.Non_terminal_property_map;
import sci.config.compilator.map.Terminal_property_map;
import sci.config.compilator.map.Token_map;
import sci.config.compilator.object.Lex_Node;
import sci.config.compilator.object.Parse_tree;
import sci.config.compilator.object.Token;
import sci.config.preprocessor.support.Token_type;

import java.util.ArrayList;

public class Syntax {

    private static Syntax instance;

    private Pushdown pushdown;
    private Token_map token_map;
    private Terminal_property_map terminal_map;
    private Non_terminal_property_map non_terminal_map;
    private Lex_map lex_map;
    private Token_type tt;

    private Syntax(){
        tt = Token_type.use();
        token_map = Token_map.use();
        lex_map   = Lex_map.use();
        terminal_map = Terminal_property_map.use();
        non_terminal_map = Non_terminal_property_map.use();
        pushdown  = Pushdown.use(token_map.delimeter_token());
        lex_map.add(new Lex_Node(token_map.delimeter_token()));
    }

    private static void init(){
        if (instance == null)
            instance = new Syntax();
    }

    public static Syntax use(){
        init();
        return instance;
    }

    public static Syntax close(){
        return instance = null;
    }

    public Parse_tree process(){

        Lex_Node pointer;
        Parse_tree tree_pointer = null;
        ArrayList<Parse_tree> tree_list = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        while (lex_map.notEmpty()){
            pointer = lex_map.out();
            if (compare(pointer)){
                construct(pointer.getToken(),temp,tree_list);
                if (terminal_map.contains_sentence(temp.toString())){
                    String new_terminal = terminal_map.getTerminal(temp.toString());
                    tree_pointer = new Parse_tree(new_terminal);
                    for (Parse_tree t:tree_list)
                        tree_pointer.add(t);
                    pushdown.push(new_terminal);
                    tree_list.clear();
                }
                temp.delete(0,temp.length()-1);
            } else {
                construct(pointer.getToken(),temp,tree_list);
                pushdown.pop();
            }
        }
        return tree_pointer;
    }

    private void construct(String token, StringBuilder temp, ArrayList<Parse_tree> tree_list){
        tree_list.add(new Parse_tree(token));
        if (temp.toString().equals(""))
            temp.append(token);
        else
            temp.append(" ").append(token);
    }

    private boolean compare(Lex_Node node) {
        String token1_key = pushdown.peek();
        String token2_key = node.getToken();
        Token token1 = token_map.get(token1_key);
        Token token2 = token_map.get(token2_key);

        return comparePrecedence(token1_key, token1, token2_key, token2);
    }

    private boolean comparePrecedence(String token1_key,Token token1,String token2_key,Token token2){
        int token_1_precedence;
        int token_2_precedence;
        if (token1.getType() == tt.NON_TERMINAL()){
            token_1_precedence = non_terminal_map.get(token1_key).getPrecedence();
        } else {
            token_1_precedence = terminal_map.get(token1_key).getPrecedence();
        }
        if (token2.getType() == tt.NON_TERMINAL()){
            token_2_precedence = non_terminal_map.get(token2_key).getPrecedence();
        } else {
            token_2_precedence = terminal_map.get(token2_key).getPrecedence();
        }
        return token_1_precedence < token_2_precedence;
    }
}
