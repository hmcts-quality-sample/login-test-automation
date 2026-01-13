package com.moj.logintest;

import static constants.Strings.ASSERT_ERROR_BANNER_NOT_DISPLAYED;
import static constants.Strings.ASSERT_INCORRECT_ERROR_MESSAGE;
import static constants.Strings.ASSERT_INCORRECT_SUCCESS_MESSAGE;
import static constants.Strings.ASSERT_SUCCESS_BANNER_NOT_DISPLAYED;
import static constants.Strings.ERROR_PASSWORD;
import static constants.Strings.ERROR_USERNAME;
import static constants.Strings.INVALID_PASSWORD;
import static constants.Strings.INVALID_USERNAME;
import static constants.Strings.LOGIN_URL;
import static constants.Strings.VALID_LOGIN_MESSAGE;
import static constants.Strings.VALID_PASSWORD;
import static constants.Strings.VALID_USERNAME;
import static helpers.TestHelper.assertContains;
import static org.testng.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("UI Automation")
@Feature("Login")
public class LoginPageTest {

  private WebDriver driver;
  private LoginPage loginPage;

  @BeforeClass
  public void setupClass() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeMethod
  public void setUp() {
    driver = DriverFactory.createChromeDriver();

    // Prefer explicit waits; if you keep implicit, keep it small
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    driver.get(LOGIN_URL);
    loginPage = new LoginPage(driver);
  }


  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    // When debugging, do not close the browser at the end of the test
    boolean debuggerAttached =
        ManagementFactory.getRuntimeMXBean()
            .getInputArguments()
            .toString()
            .contains("-agentlib:jdwp");

    if (driver != null && !debuggerAttached) {
      driver.quit();
    }
  }

  @Test(description = "Golden path: valid credentials")
  @Story("Successful login")
  @Severity(SeverityLevel.BLOCKER)
  public void goldenPath() {
    loginPage.usernameInput.sendKeys(VALID_USERNAME);
    loginPage.passwordInput.sendKeys(VALID_PASSWORD);
    loginPage.loginButton.click();
    assertTrue(loginPage.successMessage.isDisplayed(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getSuccessMessageText(), VALID_LOGIN_MESSAGE,
        ASSERT_INCORRECT_SUCCESS_MESSAGE);
  }

  @Test(description = "Invalid username shows correct error")
  @Story("Validation errors")
  @Severity(SeverityLevel.CRITICAL)  public void invalidUsernameFails() {
    loginPage.usernameInput.sendKeys(INVALID_USERNAME);
    loginPage.passwordInput.sendKeys(VALID_PASSWORD);
    loginPage.loginButton.click();
    assertTrue(loginPage.errorMessage.isDisplayed(), ASSERT_ERROR_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getErrorMessageText(), ERROR_USERNAME, ASSERT_INCORRECT_ERROR_MESSAGE);
  }

  @Test(description = "Invalid password shows correct error")
  @Story("Validation errors")
  @Severity(SeverityLevel.CRITICAL)
  public void invalidPasswordFails() {
    loginPage.usernameInput.sendKeys(VALID_USERNAME);
    loginPage.passwordInput.sendKeys(INVALID_PASSWORD);
    loginPage.loginButton.click();
    assertTrue(loginPage.errorMessage.isDisplayed(), ASSERT_ERROR_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getErrorMessageText(), ERROR_PASSWORD, ASSERT_INCORRECT_ERROR_MESSAGE);
  }

}
