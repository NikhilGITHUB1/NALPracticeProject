package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accSetUp() {
        accPage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test
    public void accPageTitleTest() {
        Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
    }

    @Test
    public void accPageURLTest(){
        Assert.assertTrue(accPage.getAccPageURL().contains(AppConstants.ACC_PAGE_URL_FRACTION));
    }

    @Test
    public void headersTest(){
        List<String> actualHeaderList = new ArrayList<>();
        List<WebElement> headersList = accPage.accountsPageHeader();

        for(WebElement header:headersList){
            actualHeaderList.add(header.getText());
        }
        Assert.assertEquals(actualHeaderList,AppConstants.ACCOUNTS_PAGE_HEADERS_LIST);
    }

    @Test
    public void search(){
        searchPage = accPage.search("Macbook pro");
    }

}
