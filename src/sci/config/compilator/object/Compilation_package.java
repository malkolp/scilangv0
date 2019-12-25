package sci.config.compilator.object;

public class Compilation_package {

    private String preprocessor_result;
    private String lexical_analysis_result;

    public Compilation_package(){
        reset();
    }

    public void set_preprocess_result(String result){
        this.preprocessor_result = result;
    }

    public void set_lexical_result(String result){
        this.lexical_analysis_result = result;
    }

    public String preprocess_result(){
        return preprocessor_result;
    }

    public String lexical_analysis_result(){
        return lexical_analysis_result;
    }

    public String[] compile_result(){
        return new String[]{preprocessor_result,lexical_analysis_result};
    }

    public String[] analysis_result(){
        return new String[]{lexical_analysis_result};
    }

    public void reset(){
        this.preprocessor_result = "";
        this.lexical_analysis_result = "";
    }
}
