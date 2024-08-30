package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;

	//locators
	private By username=By.id("input-email");
	private By password=By.id("input-password");
	private By loginBtn = By.xpath("//input1[@type='submit']");


	//page const
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}


	//page action methods
	@Step("get login page title")
	public String getLoginPageTitle() {
		String title=driver.getTitle();
		return title;

	}


	@Step("get login page url")
	public String getLoginPageURL() {
		String url=driver.getCurrentUrl();
		return url;

	}
	
	@Step("username is : {0} and password {1} ")
	public AccountsPage doLogin(String user, String pass) {
		
		driver.findElement(username).sendKeys(user);
		driver.findElement(password).sendKeys(pass);
		driver.findElement(loginBtn).click();
		
		return new AccountsPage(driver);
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


}
