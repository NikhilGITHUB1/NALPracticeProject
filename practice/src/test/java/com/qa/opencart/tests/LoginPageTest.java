package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest{
	
	@Description("login page title test allure")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("login page url test allure")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("login page login test allure")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginTest() {
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
