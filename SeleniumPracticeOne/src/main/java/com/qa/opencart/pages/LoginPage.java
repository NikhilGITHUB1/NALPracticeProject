package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    private By userName = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver){
        this.driver=driver;
        elementUtil = new ElementUtil(this.driver);
    }

    @Step("getting login page title")
    public String getLoginPageTitle(){
        String title = elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE,AppConstants.SHORT_DEFAUTT_WAIT);
        log.info("login page title"+title);
        return title;
    }

    public AccountsPage login(String username, String pwd){
        elementUtil.waitForVisibilityOfElement(userName,AppConstants.SHORT_DEFAUTT_WAIT).sendKeys(username);
        elementUtil.doSendKeys(password,pwd);
        elementUtil.doClick(loginBtn);

        return new AccountsPage(driver);
    }




}
