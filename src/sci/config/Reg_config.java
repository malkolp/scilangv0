package sci.config;

import sci.config.preprocessor.Preprocessor_cache;
import sci.config.compilator.object.Non_terminal_property;
import sci.config.compilator.object.Preprocessor_property;
import sci.config.compilator.object.Terminal_property;
import sci.config.compilator.object.Token;
import sci.config.preprocessor.processor.Processor;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg_config {

    private static Reg_config instance;

    private Pattern token_list_pattern;
    private Pattern non_terminal_property_list_pattern;
    private Pattern terminal_property_list_pattern;
    private Pattern preprocessor_property_list_pattern;
    private Pattern code_cache_list_pattern;
    private Pattern module_list_pattern;

    private Matcher matcher;

    private Reg_config(){
        token_list_pattern = Pattern.compile("^([\\w+=\\-)(*&^%$#@!~`}{\\]\\[\\\\:;\"',./<>?]+)\\s+\\|\\s+(\\d+)$");
        non_terminal_property_list_pattern = Pattern.compile("^([\\w+=\\-)(*&^%$#@!~`}{\\]\\[\\\\:;\"',./<>?]+)\\s+\\|\\s+(\\d+)\\s+\\|\\s+(\\d+)$");
        terminal_property_list_pattern = Pattern.compile("([\\w~`!@#$%^&*()\\-+=\\\\|\\]\\[{};:'\",<.>/?]+)\\s+\\|\\s+([\\w~`!@#$%^&*()\\-+=\\\\|\\]\\[{};:'\",<.>/?]+)\\s+\\|\\s+([\\d]+)");
        preprocessor_property_list_pattern = Pattern.compile("^([\\w+=\\-)(*&^%$#@!~`}{\\]\\[\\\\:;\"',./<>?]+)\\s+\\|\\s+(\\d+)$");
        code_cache_list_pattern = Pattern.compile("");
        module_list_pattern = Pattern.compile("");
    }

    private static void init(){
        if (instance == null)
            instance = new Reg_config();
    }

    static Reg_config use(){
        init();
        return instance;
    }

    static Reg_config close(){
        return instance = null;
    }

    void load_token(String directory,String file_name){
        String text = open_reader(directory, file_name);
        matcher = token_list_pattern.matcher(text);
        String key;
        int type;
        while (matcher.find()){
            key = matcher.group(1);
            type = Integer.parseInt(matcher.group(2));
            Processor.use().load_token(key,type);
        }
    }

    void load_non_terminal_property(String directory,String file_name){
        String text = open_reader(directory, file_name);
        matcher = non_terminal_property_list_pattern.matcher(text);
        String key;
        int precedence;
        int act_as;
        while (matcher.find()){
            key = matcher.group(1);
            precedence = Integer.parseInt(matcher.group(2));
            act_as = Integer.parseInt(matcher.group(3));
            Processor.use().load(key,precedence,act_as);
        }
    }

    void load_terminal_property(String directory,String file_name){
        String text = open_reader(directory, file_name);
        matcher = terminal_property_list_pattern.matcher(text);
        String key;
        String childs;
        int precedence;
        while (matcher.find()){
            key = matcher.group(1);
            childs = matcher.group(2);
            precedence = Integer.parseInt(matcher.group(3));
            Processor.use().load(key,childs,precedence);
        }
    }

    void load_preprocessor_property(String directory,String file_name){
        String text = open_reader(directory, file_name);
        matcher = preprocessor_property_list_pattern.matcher(text);
        String key;
        int preprocessor_type;
        while (matcher.find()){
            key = matcher.group(1);
            preprocessor_type = Integer.parseInt(matcher.group(2));
            Processor.use().load(key,preprocessor_type);
        }
    }

    void save_token(String directory,String file_name){
        ArrayList<Token> token = Preprocessor_cache.use().token_cache();
        ArrayList<String> token_k = Preprocessor_cache.use().token_key_cache();
        if (token_k.size() > 0){
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < token_k.size();i++)
                text.append(token_k.get(i)).append("\t|\t").append(token.get(i).getType()).append("\n");
            open_writer(directory, file_name,text.toString());
        }
    }

    void save_non_terminal_property(String directory,String file_name){
        ArrayList<Non_terminal_property> ntp = Preprocessor_cache.use().non_terminal_property();
        ArrayList<String> ntp_k = Preprocessor_cache.use().non_terminal_key_cache();
        if (ntp_k.size() > 0){
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < ntp_k.size();i++)
                text.append(ntp_k.get(i)).append("\t|\t").append(ntp.get(i).getPrecedence()).append("\t|\t").append(ntp.get(i).getAct_as()).append("\n");
            open_writer(directory, file_name,text.toString());
        }
    }

    void save_terminal_property(String directory,String file_name){
        ArrayList<Terminal_property> tp = Preprocessor_cache.use().terminal_property();
        ArrayList<String> tp_k = Preprocessor_cache.use().terminal_key_cache();
        if (tp_k.size() > 0){
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < tp_k.size();i++)
                text.append(tp_k.get(i)).append("\t|\t").append(tp.get(i).getChild()).append("\t|\t").append(tp.get(i).getPrecedence()).append("\n");
            open_writer(directory,file_name,text.toString());
        }
    }

    void save_preprocessor_property(String directory,String file_name){
        ArrayList<Preprocessor_property> pp = Preprocessor_cache.use().preprocessor_property();
        ArrayList<String> pp_k = Preprocessor_cache.use().preprocessor_key_cache();
        if (pp_k.size() > 0){
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < pp_k.size();i++)
                text.append(pp_k.get(i)).append("\t|\t").append(pp.get(i).getPreprocessor_type()).append("\n");
            open_writer(directory,file_name,text.toString());
        }
    }

    private void open_writer(String directory,String url,String text){
        File file = new File(directory+"/"+url);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String open_reader(String directory,String url){
        File file = new File(directory+"/"+url);
        try {
            if (file.exists()){
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder text = new StringBuilder();
                String tmp;
                while ((tmp = reader.readLine()) != null)
                    text.append(tmp).append("\n");
                reader.close();
                fileReader.close();
                return text.toString();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

}
