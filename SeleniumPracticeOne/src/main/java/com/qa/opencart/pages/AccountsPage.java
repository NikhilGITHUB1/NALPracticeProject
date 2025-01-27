package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountsPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    private By searchField = By.xpath("//input[@name='search']");
    private By searchIcon = By.xpath("//button[@class='btn btn-default btn-lg']");
    private By headers = By.cssSelector("div h2");

    public AccountsPage(WebDriver driver){
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public String getAccPageTitle() {
        String title = elementUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_DEFAUTT_WAIT);
        System.out.println("Acc page title:" + title);
        return title;
    }

    public String getAccPageURL(){
        String url = elementUtil.waitForURLContains(AppConstants.ACC_PAGE_URL_FRACTION,AppConstants.SHORT_DEFAUTT_WAIT);
        return url;
    }

    public List<WebElement> accountsPageHeader(){
        List<WebElement> headerList = elementUtil.visibilityOfAllElements(headers,AppConstants.SHORT_DEFAUTT_WAIT);
        return headerList;
    }

    public SearchPage search(String searchText){
        elementUtil.waitForVisibilityOfElement(searchField,AppConstants.SHORT_DEFAUTT_WAIT).sendKeys(searchText);
        elementUtil.doClick(searchIcon);
        return new SearchPage(driver);
    }






}
