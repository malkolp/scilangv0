package sci;

import sci.compiler.Compiler;
import sci.config.Config;
import sci.config.preprocessor.processor.Processor;
import sci.config.preprocessor.support.Non_terminal_actor;
import sci.config.preprocessor.support.Precedence;
import sci.config.preprocessor.support.Preprocessor_type;
import sci.config.preprocessor.support.Token_type;

public class Tool {

    public static void init(String configuration_directory_url){
        Config.use().load(configuration_directory_url);
    }

    public static Object close(Object object){
        if (object == Processor.use()) return Processor.close();
        if (object == Compiler.use())  return Compiler.close();
        return null;
    }

    public static Object close(){
        close_compiler_initialization();
        Config.close();
        Processor.close();
        Compiler.close();
        return null;
    }

    public static void close_compiler_initialization(){
        Config.use().save();
        Precedence.close();
        Token_type.close();
        Preprocessor_type.close();
        Non_terminal_actor.close();
    }

    public static Processor PROCESSOR(){
        return Processor.use();
    }

    public static Compiler COMPILER(){ return Compiler.use(); }

}
