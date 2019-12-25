package sci.config.compilator.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private static Regex instance;

    private Pattern p_alpha_num_chr;
    private Pattern p_alpha_num_str;
    private Pattern p_numeric_chr;
    private Pattern p_numeric_str;
    private Pattern p_decimal_str;
    private Pattern p_num_constant_str;
    private Pattern p_identifier_str;
    private Pattern p_keyword_str;
    private Pattern p_symbol_str;
    private Pattern p_double_type_str;
    private Pattern p_float_type_str;


    private Matcher matcher;

    private Regex(){
        p_alpha_num_chr      = Pattern.compile("^[\\w$]+");
        p_alpha_num_str      = Pattern.compile("^[\\\\w$]+$");
        p_numeric_chr        = Pattern.compile("\\d");
        p_numeric_str        = Pattern.compile("^[\\d]+$");
        p_decimal_str        = Pattern.compile("^[\\d]+.?[\\d]+$|^[\\d]+$");
        p_num_constant_str   = Pattern.compile("^[\\d]+[.]?[\\d]+[fd]?$|^[\\d]+[fd]?$");
        p_identifier_str     = Pattern.compile("^[_$]+[\\w$]+$|^[a-zA-Z$]+[\\w]*$");
        p_keyword_str        = Pattern.compile("^[a-zA-Z]+$");
        p_symbol_str         = Pattern.compile("^[^\\w]+$");
        p_double_type_str    = Pattern.compile("^[\\d]+[.][\\d]+[d]?$|^[\\d]+d$");
        p_float_type_str     = Pattern.compile("^[\\d]+[.][\\d]+[f]$|^[\\d]+f$");
    }

    private static void init(){
        if (instance == null)
            instance = new Regex();
    }

    public static Regex use(){
        init();
        return instance;
    }

    public static Regex close(){
        return instance = null;
    }

    public boolean alphanum(char chr){
        String s = ""+chr;
        matcher  = p_alpha_num_chr.matcher(s);
        return matcher.find();
    }

    public boolean alphanum(String str){
        if (str.equals("")) return true;
        matcher = p_alpha_num_str.matcher(str);
        return matcher.find();
    }

    public boolean numeric(char chr){
        String s = ""+chr;
        matcher  = p_numeric_chr.matcher(s);
        return matcher.find();
    }

    public boolean numeric(String str){
        matcher  = p_numeric_str.matcher(str);
        return matcher.find();
    }

    public boolean decimal(String str){
        matcher = p_decimal_str.matcher(str);
        return matcher.find();
    }

    public boolean is_F_or_D(char chr){
        if (chr == 'f') return true;
        return chr == 'd';
    }

    public boolean isDot(char chr){
        return chr == '.';
    }

    public boolean lastDot(String str){
        return str.charAt(str.length()-1) == '.';
    }

    public boolean quoteMarks(char chr){
        return chr == '\"';
    }

    public boolean escaped(String str){
        if (str.length() < 1) return false;
        return str.charAt(str.length() - 1) == '\\';
    }

    public boolean isSpace(char chr){
        return chr == ' ';
    }

    public boolean isNotEmpty(String str){
        return !str.equals("");
    }

    public boolean numConstant(String str){
        matcher = p_num_constant_str.matcher(str);
        return matcher.find();
    }

    public boolean identifier(String key){
        matcher = p_identifier_str.matcher(key);
        return matcher.find();
    }

    public boolean keyword(String key){
        matcher = p_keyword_str.matcher(key);
        return matcher.find();
    }

    public boolean symbol(String key){
        matcher = p_symbol_str.matcher(key);
        return matcher.find();
    }

    public boolean doubleType(String value){
        matcher = p_double_type_str.matcher(value);
        return matcher.find();
    }

    public boolean floatType(String value){
        matcher = p_float_type_str.matcher(value);
        return matcher.find();
    }
}
