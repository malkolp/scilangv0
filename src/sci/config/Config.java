package sci.config;

import sci.config.preprocessor.Preprocessor_cache;

public class Config {

    private static Config instance;
    private static Reg_config regex;

    private String config_file_directory;
    private String token_url;
    private String non_terminal_property_url;
    private String terminal_property_url;
    private String preprocessor_property_url;
    private String code_cache_url;
    private String module_url;

    private Config(){
        regex = Reg_config.use();
        token_url = "_token.sconf";
        non_terminal_property_url = "_ntp.sconf";
        terminal_property_url = "_tp.sconf";
        preprocessor_property_url = "_pp.sconf";
        code_cache_url = "_cc.scurl";
        module_url = "_module.scurl";
    }

    private static void init(){
        if (instance == null)
            instance = new Config();
    }

    public static Config use(){
        init();
        return instance;
    }

    public static Config close(){
        regex = Reg_config.close();
        return instance = null;
    }

    public void load(String main_directory_url){
        this.config_file_directory = main_directory_url;
        load_sources();
    }

    private void load_sources(){
        regex.load_token(config_file_directory,token_url);
        regex.load_preprocessor_property(config_file_directory,preprocessor_property_url);
        regex.load_non_terminal_property(config_file_directory,non_terminal_property_url);
        regex.load_terminal_property(config_file_directory,terminal_property_url);
    }

    public void save(){
        Thread t = new Thread(() -> {
            regex.save_token(config_file_directory,token_url);
            regex.save_non_terminal_property(config_file_directory,non_terminal_property_url);
            regex.save_terminal_property(config_file_directory,terminal_property_url);
            regex.save_preprocessor_property(config_file_directory,preprocessor_property_url);
            Preprocessor_cache.close();
        });
        t.start();
    }

}
