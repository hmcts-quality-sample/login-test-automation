package com.moj.logintest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


// page_url = https://the-internet.herokuapp.com/secure
public class SecurePage {

  private final WebDriver driver;

  private static final By FLASH_SUCCESS = By.cssSelector(".flash.success");
  private static final By SECURE_HEADER = By.cssSelector("#content h2"); // "Secure Area"
  private static final By LOGOUT_BUTTON = By.cssSelector("a.button[href='/logout']");

  public SecurePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(css = ".flash.success")
  private WebElement successMessage;

  public String getSuccessMessageText() {
    return successMessage.getText().trim();
  }

  public boolean isSuccessMessagePresent() {
    return !driver.findElements(FLASH_SUCCESS).isEmpty();
  }


  public boolean isSecurePageLoaded() {
    try {
      waitUntilLoaded(Duration.ofSeconds(5));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isSuccessMessageDisplayed() {
    return isSuccessMessagePresent() && successMessage.isDisplayed();
  }

  public void logout() {
    waitUntilLoaded(Duration.ofSeconds(5));
    driver.findElement(LOGOUT_BUTTON).click();
  }

  public void waitUntilLoaded(Duration timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
    wait.ignoring(org.openqa.selenium.NoSuchElementException.class);

    wait.until(ExpectedConditions.urlContains("/secure"));

    wait.until(d -> "complete".equals(
        ((JavascriptExecutor) d).executeScript("return document.readyState")));

    wait.until(ExpectedConditions.visibilityOfElementLocated(SECURE_HEADER));
    wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
  }

}
