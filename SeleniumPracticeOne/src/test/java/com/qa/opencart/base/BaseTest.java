package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.SearchPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.Properties;


public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;
    DriverFactory df;
    protected LoginPage loginPage;
    protected SoftAssert softAssert;
    protected AccountsPage accPage;
    protected SearchPage searchPage;

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @Parameters({"browser","browserversion","testname"})
    @BeforeTest
    public void setUp(String browserName, String browserVersion,String testName){
            log.info(browserName + ":" + browserVersion + " " + testName);
            df = new DriverFactory();
            prop = df.initProp();

            if(browserName!=null){
                prop.setProperty("browser",browserName);
                prop.setProperty("browserversion", browserVersion);
                prop.setProperty("testname",testName);
            }

            driver = df.initDriver(prop);
            loginPage = new LoginPage(driver);
            softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
        log.info("browser is closed");
    }

}
