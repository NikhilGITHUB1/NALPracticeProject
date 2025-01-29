package com.qa.gorest.configuration;

import com.qa.gorest.frameworkexception.APIFrameworkException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

    private Properties prop;
    private FileInputStream ip;

    public Properties initProp(){
        prop = new Properties();

        String envName = System.getProperty("env");

        try{
            if(envName == null){
                System.out.println("no env , running on QA env");
                ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
            }
            else{
                System.out.println("running on test env:"+envName);

            switch (envName.toLowerCase().trim()) {
                case "qa":
                    ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                    break;
                case "dev":
                    ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
                default:
                    System.out.println("please pass the right env name");
                    throw new APIFrameworkException("Wrong env is given");
            }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }



}
