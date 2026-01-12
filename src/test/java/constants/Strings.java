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
  public static final String INVALID_LOGIN_MESSAGE =
      "Your username is invalid!";

  // Prevent instantiation
  private Strings() {
  }
}
