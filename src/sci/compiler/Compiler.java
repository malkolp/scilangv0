package sci.compiler;

import sci.compiler.lexical.Lexer;
import sci.compiler.preprocess.Preprocess;
import sci.compiler.syntactic.Syntax;
import sci.config.compilator.object.Compilation_package;
import sci.config.compilator.object.Parse_tree;

public class Compiler {

    private static Compiler instance;

    private Compilation_package compilation_package;

    private Compiler(){
        compilation_package = new Compilation_package();
    }

    private static void init(){
        if (instance == null)
            instance = new Compiler();
    }

    public static Compiler use(){
        init();
        return instance;
    }

    public static Compiler close(){
        Preprocess.close();
        return instance = null;
    }

    public void compile(String main_code_url){
        String code = Preprocess.use().read(main_code_url);
        Lexer.use().lex(code);
        Parse_tree tree = Syntax.use().process();
    }

    public void reset(){
        compilation_package.reset();
    }
}
