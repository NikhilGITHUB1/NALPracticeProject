package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	//WebDriver driver;
	Properties prop;
	OptionsManager options;

	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();

	//initialize driver
	public WebDriver initDriver(Properties prop) {

		String browser=prop.getProperty("browser");
		options=new OptionsManager(prop);

		switch(browser.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(options.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(options.getFirefoxOptions()));

			break;
		default:
			throw new FrameworkException("No Browser Found...");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}


	public static WebDriver getDriver() {
		return tlDriver.get();
	}


	//initialize prop
	public Properties initProp() {

		FileInputStream ip = null;
		prop=new Properties();

		String envName=System.getProperty("env");

		try {
			if(envName==null) {
				ip=new FileInputStream("./src/test/resources/config/config.qa.properties");
			}
			else{
				switch(envName.toLowerCase().trim()) {
				case "qa":
					ip=new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "dev":
					ip=new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;

				default:
					throw new FrameworkException("Wrong env name"+envName);
				}
			}
		}

		catch (FileNotFoundException e) {

		}
		try {
			prop.load(ip);
		}catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}	


	//take screenshot
	public static String getScreenshot(String methodName) {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
		+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return path;

	}










}



