package com.qa.opencart.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ElementUtil {

    private WebDriver driver;
    private JavaScriptUtil jsUtil;


    public ElementUtil(WebDriver driver) {
        this.driver=driver;
        jsUtil = new JavaScriptUtil(this.driver);
    }

    @Step("Waiting for the page title :{0} and timeout:{1}")
    public String waitForTitleIs(String title, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
        try{
            if(wait.until(ExpectedConditions.titleIs(title))){
                return driver.getTitle();
            }
        } catch (TimeoutException e){
            System.out.println(title + "title value is not present..");
            return null;
        }
        return driver.getTitle();
    }

    @Step("Waiting for the visibility of element")
    public WebElement waitForVisibilityOfElement(By locator, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return webElement;
    }

    public void doSendKeys(By locator, String value){
        getElement(locator).sendKeys(value);
    }

    private WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public void doClick(By locator){
        getElement(locator).click();
    }

    public String waitForURLContains(String urlFraction , int timeout){
        WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
                return driver.getCurrentUrl();
            }
        }
        catch(TimeoutException e){
            System.out.println(urlFraction + "url is not present");
        }
        return null;
    }

    public List<WebElement> visibilityOfAllElements(By headers, int shortDefauttWait) {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(shortDefauttWait));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(headers));
    }
}
