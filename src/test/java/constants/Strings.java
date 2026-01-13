package constants;

public final class Strings {

  // ---------------------------
  // URLs
  // ---------------------------

  public static final String LOGIN_URL =
      "https://the-internet.herokuapp.com/login";

  public static final String SECURE_URL =
      "https://the-internet.herokuapp.com/secure";

  // ---------------------------
  // Credentials (demo only)
  // ---------------------------

  public static final String VALID_USERNAME = "tomsmith";
  public static final String VALID_PASSWORD = "SuperSecretPassword!";
  public static final String INVALID_USERNAME = "invalidUser";
  public static final String INVALID_PASSWORD = "invalidPassword";

  // ---------------------------
  // Application messages
  // ---------------------------

  public static final String VALID_LOGGED_IN_MESSAGE =
      "You logged into a secure area!";

  public static final String VALID_LOGGED_OUT_MESSAGE =
      "You logged out of the secure area!";

  public static final String ERROR_USERNAME =
      "Your username is invalid!";

  public static final String ERROR_PASSWORD =
      "Your password is invalid!";

  public static final String ERROR_NOT_AUTHENTICATED =
      "You must login to view the secure area!";

  // ---------------------------
  // Assert failure messages
  // ---------------------------

  public static final String ASSERT_SUCCESS_BANNER_NOT_DISPLAYED =
      "ASSERT: Failed to display success banner";

  public static final String ASSERT_SUCCESS_BANNER_INCORRECTLY_DISPLAYED =
      "ASSERT: Failed to hide success banner";

  public static final String ASSERT_INCORRECT_SUCCESS_MESSAGE =
      "ASSERT: Failed to display correct success message";

  public static final String ASSERT_ERROR_BANNER_NOT_DISPLAYED =
      "ASSERT: Failed to display error banner";

  public static final String ASSERT_INCORRECT_ERROR_MESSAGE =
      "ASSERT: Failed to display correct error message";

  public static final String ASSERT_REDIRECT_ERROR_MESSAGE =
      "ASSERT: Failed to redirect to Login Page";

  public static final String ASSERT_SECURE_PAGE_NOT_LOADED =
      "ASSERT: Secure page was not loaded as expected";

  public static final String ASSERT_LOGIN_PAGE_NOT_DISPLAYED =
      "ASSERT: Login page should be displayed";

  // Prevent instantiation
  private Strings() {
  }
}
