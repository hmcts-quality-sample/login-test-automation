package com.moj.logintest;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {

  private DriverFactory() {
  }

  public static WebDriver createChromeDriver() {
    boolean headless = resolveHeadless();

    ChromeOptions options = new ChromeOptions();

    // Window sizing is useful for consistent element layout
    options.addArguments("--window-size=1280,800");

    // Headless toggle
    if (headless) {
      // "new" headless is the modern mode for recent Chrome
      options.addArguments("--headless=new");
    }

    // CI stability (safe to keep for local too)
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu"); // mostly irrelevant on Linux, harmless

    // Reduce automation noise / prompts
    options.addArguments("--disable-notifications");
    options.addArguments("--disable-popup-blocking");
    options.addArguments("--no-first-run");
    options.addArguments("--no-default-browser-check");

    // Disable password manager / credential prompts (incl. breach-related UI)
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);

    // Optional: disable Safe Browsing related checks that can trigger password leak UI.
    // (This is for test environments only.)
    prefs.put("safebrowsing.enabled", false);

    options.setExperimentalOption("prefs", prefs);

    // Reduce "Chrome is being controlled..." infobar noise
    options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    options.setExperimentalOption("useAutomationExtension", false);

    return new ChromeDriver(options);
  }

  private static boolean resolveHeadless() {
    String sys = System.getProperty("headless");
    if (sys != null) return Boolean.parseBoolean(sys);

    String env = System.getenv("HEADLESS");
    if (env != null) return Boolean.parseBoolean(env);

    String ci = System.getenv("CI");
    if (ci != null && ci.equalsIgnoreCase("true")) return true;

    // Local default: headed
    return false;
  }

}
