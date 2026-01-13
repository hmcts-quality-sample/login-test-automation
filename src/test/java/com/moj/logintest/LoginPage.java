package com.moj.logintest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://the-internet.herokuapp.com/login
public class LoginPage {

  @FindBy(xpath = "//*[@name='username']")
  public WebElement usernameInput;

  @FindBy(xpath = "//*[@name='password']")
  public WebElement passwordInput;

  @FindBy(css = "form#login button[type='submit']")
  public WebElement loginButton;

  @FindBy(css = ".flash.success")
  public WebElement successMessage;

  @FindBy(css = ".flash.error")
  public WebElement errorMessage;

  public LoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }

  public String getSuccessMessageText() {
    return successMessage.getText().trim();
  }

  public String getErrorMessageText() {
    return errorMessage.getText().trim();
  }
}
