package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsPage {
	
	private WebDriver driver;
	
	private By logoutLink=By.linkText("Logout");

	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		
	}

	public boolean isLogoutLinkExist() {
		return driver.findElement(logoutLink).isDisplayed();
	}

}
