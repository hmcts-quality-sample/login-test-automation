package com.moj.logintest;

import static constants.Strings.ASSERT_ERROR_BANNER_NOT_DISPLAYED;
import static constants.Strings.ASSERT_INCORRECT_ERROR_MESSAGE;
import static constants.Strings.ASSERT_INCORRECT_SUCCESS_MESSAGE;
import static constants.Strings.ASSERT_LOGIN_PAGE_NOT_DISPLAYED;
import static constants.Strings.ASSERT_REDIRECT_ERROR_MESSAGE;
import static constants.Strings.ASSERT_SECURE_PAGE_NOT_LOADED;
import static constants.Strings.ASSERT_SUCCESS_BANNER_INCORRECTLY_DISPLAYED;
import static constants.Strings.ASSERT_SUCCESS_BANNER_NOT_DISPLAYED;
import static constants.Strings.ERROR_NOT_AUTHENTICATED;
import static constants.Strings.ERROR_PASSWORD;
import static constants.Strings.ERROR_USERNAME;
import static constants.Strings.INVALID_PASSWORD;
import static constants.Strings.INVALID_USERNAME;
import static constants.Strings.LOGIN_URL;
import static constants.Strings.SECURE_URL;
import static constants.Strings.VALID_LOGGED_IN_MESSAGE;
import static constants.Strings.VALID_LOGGED_OUT_MESSAGE;
import static constants.Strings.VALID_PASSWORD;
import static constants.Strings.VALID_USERNAME;
import static helpers.TestHelper.assertContains;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Login Page Automation Framework (Assessment)")
public class LoginPageTest {

  private WebDriver driver;
  private LoginPage loginPage;
  private SecurePage securePage;

  @BeforeClass
  public void setupClass() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeMethod
  public void setUp() {
    driver = DriverFactory.createChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    driver.get(LOGIN_URL);
    loginPage = new LoginPage(driver);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    boolean debuggerAttached = ManagementFactory.getRuntimeMXBean().getInputArguments().toString()
        .contains("-agentlib:jdwp");

    if (driver != null && !debuggerAttached) {
      driver.quit();
    }
  }

  // ---------------------------
  // Authorization (unauthorized access)
  // ---------------------------

  @Test(description = "Given no authenticated session, when the user navigates directly to a secure page, then access is denied")
  @Feature("Authorization")
  @Story("Unauthorized access prevention")
  @Severity(SeverityLevel.BLOCKER)
  public void givenNoAuthenticatedSession_whenNavigatingDirectlyToSecurePage_thenAccessIsDenied() {
    driver.navigate().to(SECURE_URL);

    assertEquals(driver.getCurrentUrl(), LOGIN_URL, ASSERT_REDIRECT_ERROR_MESSAGE);

    assertTrue(loginPage.isErrorMessageDisplayed(), ASSERT_ERROR_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getErrorMessageText(), ERROR_NOT_AUTHENTICATED,
        ASSERT_INCORRECT_ERROR_MESSAGE);
  }

  @Test(description = "Given a logged out user, when they attempt to access a secure page, then access is denied and they are redirected to login")
  @Feature("Authorization")
  @Story("Post-logout access control")
  @Severity(SeverityLevel.BLOCKER)
  public void givenLoggedOutUser_whenAccessingSecurePage_thenAccessIsDeniedAndRedirectedToLogin() {
    securePage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

    assertTrue(securePage.isSuccessMessagePresent(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);
    assertTrue(securePage.isSuccessMessageDisplayed(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);

    securePage.logout();

    assertEquals(driver.getCurrentUrl(), LOGIN_URL, ASSERT_REDIRECT_ERROR_MESSAGE);
    assertTrue(loginPage.isLoginPageDisplayed(), ASSERT_LOGIN_PAGE_NOT_DISPLAYED);

    driver.get(SECURE_URL);

    assertEquals(driver.getCurrentUrl(), LOGIN_URL, ASSERT_REDIRECT_ERROR_MESSAGE);
    assertTrue(loginPage.isLoginPageDisplayed(), ASSERT_LOGIN_PAGE_NOT_DISPLAYED);

    assertTrue(loginPage.isErrorMessageDisplayed(), ASSERT_ERROR_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getErrorMessageText(), ERROR_NOT_AUTHENTICATED,
        ASSERT_INCORRECT_ERROR_MESSAGE);
  }

  // ---------------------------
  // Authentication (login behaviour)
  // ---------------------------

  @Test(description = "Given valid credentials, when the user logs in, then a success banner is displayed with the expected message")
  @Feature("Authentication")
  @Story("Login success")
  @Severity(SeverityLevel.CRITICAL)
  public void givenValidCredentials_whenUserLogsIn_thenSuccessBannerIsDisplayedWithExpectedMessage() {
    securePage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

    assertTrue(securePage.isSuccessMessagePresent(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);
    assertTrue(securePage.isSuccessMessageDisplayed(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);

    assertContains(securePage.getSuccessMessageText(), VALID_LOGGED_IN_MESSAGE,
        ASSERT_INCORRECT_SUCCESS_MESSAGE);
  }

  @Test(description = "Given an invalid username, when the user attempts to log in, then an error banner is displayed with the expected message")
  @Feature("Authentication")
  @Story("Login validation errors")
  @Severity(SeverityLevel.CRITICAL)
  public void givenInvalidUsername_whenUserLogsIn_thenErrorBannerIsDisplayedWithExpectedMessage() {
    loginPage.login(INVALID_USERNAME, VALID_PASSWORD);

    assertTrue(loginPage.isErrorMessageDisplayed(), ASSERT_ERROR_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getErrorMessageText(), ERROR_USERNAME, ASSERT_INCORRECT_ERROR_MESSAGE);
  }

  @Test(description = "Given an invalid password, when the user attempts to log in, then an error banner is displayed with the expected message")
  @Feature("Authentication")
  @Story("Login validation errors")
  @Severity(SeverityLevel.CRITICAL)
  public void givenInvalidPassword_whenUserLogsIn_thenErrorBannerIsDisplayedWithExpectedMessage() {
    loginPage.login(VALID_USERNAME, INVALID_PASSWORD);

    assertTrue(loginPage.isErrorMessageDisplayed(), ASSERT_ERROR_BANNER_NOT_DISPLAYED);
    assertContains(loginPage.getErrorMessageText(), ERROR_PASSWORD, ASSERT_INCORRECT_ERROR_MESSAGE);
  }

  // ---------------------------
  // Post-login behaviour
  // ---------------------------

  @Test(description = "Given an authenticated session, when the page is refreshed, then the secure page remains accessible and the success banner is not shown again")
  @Feature("Authentication")
  @Story("Post-login user experience")
  @Severity(SeverityLevel.NORMAL)
  public void givenAuthenticatedSession_whenPageIsRefreshed_thenSuccessBannerIsNotShownAgain() {
    securePage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

    assertTrue(securePage.isSuccessMessagePresent(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);
    assertTrue(securePage.isSuccessMessageDisplayed(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);

    driver.navigate().refresh();

    assertTrue(securePage.isSecurePageLoaded(), ASSERT_SECURE_PAGE_NOT_LOADED);
    assertFalse(securePage.isSuccessMessagePresent(), ASSERT_SUCCESS_BANNER_INCORRECTLY_DISPLAYED);
  }

  // ---------------------------
  // Logout
  // ---------------------------

  @Test(description = "Given an authenticated user, when they log out, then they are redirected to the login page with a confirmation message")
  @Feature("Authentication")
  @Story("Logout and session termination")
  @Severity(SeverityLevel.CRITICAL)
  public void givenAuthenticatedUser_whenUserLogsOut_thenRedirectedToLoginWithConfirmationMessage() {
    securePage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

    assertTrue(securePage.isSuccessMessagePresent(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);
    assertTrue(securePage.isSuccessMessageDisplayed(), ASSERT_SUCCESS_BANNER_NOT_DISPLAYED);

    securePage.logout();

    assertEquals(driver.getCurrentUrl(), LOGIN_URL, ASSERT_REDIRECT_ERROR_MESSAGE);

    assertTrue(loginPage.isLoginPageDisplayed(), ASSERT_LOGIN_PAGE_NOT_DISPLAYED);
    assertContains(loginPage.getSuccessMessageText(), VALID_LOGGED_OUT_MESSAGE,
        ASSERT_INCORRECT_SUCCESS_MESSAGE);
  }
}
