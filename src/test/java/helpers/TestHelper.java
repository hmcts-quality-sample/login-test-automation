package helpers;

import static org.testng.Assert.assertTrue;

public final class TestHelper {

  private TestHelper() {
    // Utility class – prevent instantiation
  }

  public static void assertContains(
      String actual,
      String expected,
      String contextMessage
  ) {
    assertTrue(
        actual != null && actual.contains(expected),
        String.format(
            "%s%nExpected to contain: [%s]%nActual: [%s]",
            contextMessage,
            expected,
            actual
        )
    );
  }

}
