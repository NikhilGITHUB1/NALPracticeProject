package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

public class SearchPage {

    private WebDriver driver;
    private ElementUtil elementUtil;
    public SearchPage(WebDriver driver){
        this.driver= driver;
        elementUtil = new ElementUtil(driver);
    }


}
