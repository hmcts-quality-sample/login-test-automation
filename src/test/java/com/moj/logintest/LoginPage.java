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

// page_url = https://the-internet.herokuapp.com/login
public class LoginPage {

  private final WebDriver driver;

  private static final By LOGIN_FORM = By.cssSelector("form#login");
  private static final By FLASH_SUCCESS = By.cssSelector(".flash.success");
  private static final By FLASH_ERROR = By.cssSelector(".flash.error");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(css = "#username")
  public WebElement usernameInput;

  @FindBy(css = "#password")
  public WebElement passwordInput;

  @FindBy(css = "form#login button[type='submit']")
  public WebElement loginButton;

  @FindBy(css = ".flash.error")
  public WebElement errorMessage;

  @FindBy(css = ".flash.success")
  public WebElement successMessage;

  public SecurePage login(String username, String password) {
    usernameInput.clear();
    usernameInput.sendKeys(username);

    passwordInput.clear();
    passwordInput.sendKeys(password);

    loginButton.click();
    return new SecurePage(driver);
  }

  public boolean isLoginPageDisplayed() {
    return !driver.findElements(LOGIN_FORM).isEmpty() && driver.getCurrentUrl().contains("/login");
  }

  public boolean isErrorMessagePresent() {
    return !driver.findElements(FLASH_ERROR).isEmpty();
  }

  public String getSuccessMessageText() {
    return successMessage.getText().trim();
  }

  public String getErrorMessageText() {
    return errorMessage.getText().trim();
  }

  public boolean isErrorMessageDisplayed() {
    return isErrorMessagePresent() && errorMessage.isDisplayed();
  }

  public void waitUntilLoaded(Duration timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
    wait.ignoring(org.openqa.selenium.NoSuchElementException.class);

    wait.until(ExpectedConditions.urlContains("/login"));

    wait.until(d -> "complete".equals(
        ((JavascriptExecutor) d).executeScript("return document.readyState")));

    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
  }
}
