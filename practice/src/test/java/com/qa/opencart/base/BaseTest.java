package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;

public class BaseTest {

	protected WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	DriverFactory df;
	
	
	
	@Parameters({"browser","browserversion","testname"})
	@BeforeTest
	public void setup(String browserName,String browserVersion,String testName) {
		df=new DriverFactory();
		prop=df.initProp();
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);
            prop.setProperty("testname", testName);
		}
		
		driver=df.initDriver(prop); // tlDriver is returned here, same tlDriver being used everywhere
		loginPage=new LoginPage(driver);	
	}
	
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
	}
		
	
	
	
}
