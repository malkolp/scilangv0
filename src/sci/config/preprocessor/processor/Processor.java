package sci.config.preprocessor.processor;

import sci.config.preprocessor.Preprocessor_cache;
import sci.config.compilator.map.Token_map;
import sci.config.compilator.object.Token;

public class Processor {

    private static Processor instance;
    private static Terminal terminal;
    private static Non_terminal non_terminal;
    private static Preprocessor preprocessor;

    private Processor(){}

    private static void init(){
        if (instance == null){
            instance     = new Processor();
            terminal     = Terminal.use();
            non_terminal = Non_terminal.use();
            preprocessor = Preprocessor.use();
        }
    }

    public static Processor use(){
        init();
        return instance;
    }

    public static Processor close(){
        terminal = Terminal.close();
        non_terminal = Non_terminal.close();
        preprocessor = Preprocessor.close();
        return instance = null;
    }

    public boolean add(String token,int preprocessor_type){
        return preprocessor.add(token, preprocessor_type);
    }

    public boolean add(String token, int precedence,int act_as){
        return non_terminal.add(token, precedence, act_as);
    }

    public boolean add(String key,String child,int precedence){
        return terminal.add(key, child, precedence);
    }

    public boolean add(String key,String child){
        return terminal.add(key, child);
    }

    public void load_token(String key, int token_type){
        Token token = new Token(token_type);
        if (Token_map.use().add(key,token)){
            Preprocessor_cache.use().add(token);
        }
    }

    public void load(String token, int preprocessor_type){
        preprocessor.load(token, preprocessor_type);
    }

    public void load(String token, int precedence, int act_as){
        non_terminal.load(token, precedence, act_as);
    }

    public void load(String token, String child, int precedence){
        terminal.load(token, child, precedence);
    }
}
