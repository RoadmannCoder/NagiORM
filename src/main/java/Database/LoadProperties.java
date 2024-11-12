package main.java.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
    private static LoadProperties INSTANCE = null;
    private Properties properties;
    private LoadProperties() throws IOException {
        getProperties();
    }
    public static LoadProperties getInstance() throws IOException {
        if (INSTANCE == null){
            INSTANCE = new LoadProperties();
        }
        return INSTANCE;
    }
    public Properties getProperty(){
        return properties;
    }
    private void getProperties() throws IOException {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/DBconfig.properties");
            properties.load(fileInputStream);
        }


}
