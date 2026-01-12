package com.moj.logintest;

import static constants.Strings.LOGIN_URL;
import static constants.Strings.VALID_PASSWORD;
import static constants.Strings.VALID_USERNAME;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginPageTest {

  private WebDriver driver;
  private LoginPage loginPage;

  @BeforeClass
  public void setupClass() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeMethod
  public void setUp() {
    boolean headless = Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "true"));

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--window-size=1920,1080");

    if (headless) {
      options.addArguments("--headless=new");
    }

    // Disable Chrome password manager prompts / breach warnings
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    options.setExperimentalOption("prefs", prefs);
    options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");

    driver = new ChromeDriver(options);

    // Prefer explicit waits; if you keep implicit, keep it small
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

    driver.get(LOGIN_URL);
    loginPage = new LoginPage(driver);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void goldenPath() {
    loginPage.usernameInput.sendKeys(VALID_USERNAME);
    loginPage.passwordInput.sendKeys(VALID_PASSWORD);
    loginPage.loginButton.click();
  }

}
