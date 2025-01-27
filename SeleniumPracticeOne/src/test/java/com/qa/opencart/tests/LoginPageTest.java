package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.management.DescriptorKey;

public class LoginPageTest extends BaseTest {

    private static final Logger log  = LogManager.getLogger(LoginPageTest.class);

    @Description("login page title test")
    @Test
    public void loginPageTitleTest(){
        String actTitle = loginPage.getLoginPageTitle();
        log.info("actual login page title:"+actTitle);
        Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Description("login")
    @Test(priority = 1)
    public void login(){
        loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
    }


}
