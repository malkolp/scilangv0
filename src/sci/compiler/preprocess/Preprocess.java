package sci.compiler.preprocess;

import sci.config.compilator.map.Preprocessor_property_map;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocess {

    private static Preprocess instance;

    private String fileRead;
    private String code;

    private Preprocess(){
        fileRead = "";
        code = "";
    }

    private static void init(){
        if (instance == null){
            instance = new Preprocess();
        }
    }

    public static Preprocess use(){
        init();
        return instance;
    }

    public static Preprocess close(){
        return instance = null;
    }

    public String read(String url){
        readChild(url);
        this.fileRead = "";
        return getCode();
    }

    private String getCode(){
        String toReturn = this.code;
        this.code = "";
        return toReturn;
    }

    private void readChild(String url){
        if (notContainsFile(url)){
            code = code + readFile(url);
            String[] importList = readImport(code);
            for (String importObject:importList){
                readChild(importObject);
            }
        }
    }

    private boolean notContainsFile(String file){
        Pattern p = Pattern.compile("<<"+file+">>");
        Matcher m = p.matcher(fileRead);
        if (m.find())
            return false;
        fileRead = fileRead+"<<"+file+">>";
        return true;
    }

    private String readFile(String url){
        File file = new File(url);
        StringBuilder text = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String tmp;
            while ((tmp = br.readLine()) != null)
                text.append(tmp);
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    private String[] readImport(String code){
        ArrayList<String> importList = new ArrayList<>();
        String import_key = Preprocessor_property_map.use().importKey()+"\\s*\"([\\w\\s/]+.sci)\"\\s*;";
        Pattern p = Pattern.compile(import_key);
        Matcher m = p.matcher(code);
        while (m.find()){
            importList.add(m.group(1));
        }
        this.code = m.replaceAll(Preprocessor_property_map.use().importKey()+";");
        return (String[]) importList.toArray();
    }

}
