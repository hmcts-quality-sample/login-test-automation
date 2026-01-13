package constants;

public final class Strings {

  // URLs
  public static final String LOGIN_URL =
      "https://the-internet.herokuapp.com/login";
  // Credentials (demo only)
  public static final String VALID_USERNAME = "tomsmith";
  public static final String VALID_PASSWORD = "SuperSecretPassword!";
  public static final String INVALID_USERNAME = "invalidUser";
  public static final String INVALID_PASSWORD = "invalidPassword";
  // Messages
  public static final String ERROR_USERNAME =
      "Your username is invalid!";

  public static final String ERROR_PASSWORD =
      "Your password is invalid!";


  public static final String VALID_LOGIN_MESSAGE =
      "You logged into a secure area!";

  // Asser failure messages
  public static final String ASSERT_SUCCESS_BANNER_NOT_DISPLAYED =
      "ASSERT: Failed to display success banner";

  public static final String ASSERT_INCORRECT_SUCCESS_MESSAGE =
      "ASSERT: Failed to display correct success message";

  public static final String ASSERT_ERROR_BANNER_NOT_DISPLAYED =
      "ASSERT: Failed to display error banner";

  public static final String ASSERT_INCORRECT_ERROR_MESSAGE =
      "ASSERT: Failed to display correct error message";

  // Prevent instantiation
  private Strings() {
  }
}
