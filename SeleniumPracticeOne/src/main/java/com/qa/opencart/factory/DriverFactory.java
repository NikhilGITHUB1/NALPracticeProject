package com.qa.opencart.factory;

import com.qa.opencart.exceptions.FrameworkException;
import io.qameta.allure.util.PropertiesUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.webaudio.model.AudioListenerWillBeDestroyed;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

public class DriverFactory {

    WebDriver driver;
    Properties prop;
    OptionsManager optionsManager;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    public static final Logger log = LogManager.getLogger(DriverFactory.class);

    public static String highlight=null;

    public WebDriver initDriver(Properties prop){

        String browserName = prop.getProperty("browser");
        log.info("browser name is" + browserName);

        highlight = prop.getProperty("highlight");

        optionsManager = new OptionsManager(prop);

        switch(browserName.toLowerCase().trim()){
            case "chrome":
                log.info("Running it on chrome browser");
                if(Boolean.parseBoolean(prop.getProperty("remote"))){
                    log.info("running it on remote machine");
                    initRemoteDriver(browserName);
                }
                else{
                    log.info("running it on local");
                    tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
                }
                break;

            case "firefox":
                log.info("Running it on chrome browser");
                if(Boolean.parseBoolean(prop.getProperty("remote"))){
                    log.info("running it on remote machine");
                    initRemoteDriver(browserName);
                }
                else{
                    log.info("running it on local");
                    tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
                }
                break;

            default:
                log.warn("please pass the right browserName:"+browserName);
                throw new FrameworkException("no browsers found");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));

        return getDriver();
    }

    private void initRemoteDriver(String browserName) {

        System.out.println("Running tests on grid with browser:"+browserName);
        String hubUrl = prop.getProperty("huburl");
        if(hubUrl==null || hubUrl.isEmpty()){
            throw new FrameworkException("huburl is not configured");
        }
        try{
            switch (browserName.toLowerCase().trim()){
                case "chrome":
                    tlDriver.set(new RemoteWebDriver(new URL(hubUrl), optionsManager.getChromeOptions()));
                    break;
                case "firefox":
                    tlDriver.set(new RemoteWebDriver(new URL(hubUrl), optionsManager.getFirefoxOptions()));
                    break;
                default:
                    System.out.println("wrong info..cannot run on grid machine");
                    break;
            }

        }catch(MalformedURLException e){
                throw new FrameworkException("Invalid grid url:" + prop.getProperty("huburl"), e);
        }


    }



    public static WebDriver getDriver(){
        return tlDriver.get();
        // static means we can access the driver without the DriverFactory class else object creation was needed
    }

    public Properties initProp(){
        FileInputStream ip = null;
        prop = new Properties();

        String envName = System.getProperty("env");

        try{
            if(envName==null){
                ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
            }
            else{
                switch(envName.toLowerCase().trim()){
                    case "qa":
                        ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
                        break;
                    case "uat":
                        ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
                        break;

                    default:
                        throw new FrameworkException("Wrong env name"+envName);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            prop.load(ip);
        }catch (IOException e){
            e.printStackTrace();
        }

        return prop;
    }

    public static String getScreenshot(String methodName){

        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
        File destination = new File(path);

        try{
            FileHandler.copy(srcFile,destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;

    }



}
