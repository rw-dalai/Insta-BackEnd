package com.example.insta.foundation;

import java.util.Collection;
import org.springframework.util.Assert;

/**
 * Assertion utility class that assists in validating arguments. We also use Spring's Assert class
 * here and in our domain models.
 */
public abstract class AssertUtil {
  // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression
  // A simple email pattern that is not 100% correct but good enough for our use case.
  private static final String emailPattern = "^(.+)@(\\S+)$";

  /**
   * Assert that the given email is valid.
   *
   * @param email the email to check
   * @param message the exception message to use if the assertion fails
   */
  public static void isValidEmail(String email, String message) {
    Assert.hasText(email, message);
    Assert.isTrue(email.matches(emailPattern), message);
  }

  /**
   * Assert that the given text has maximum length.
   *
   * @param text the text to check
   * @param max the maximum length
   * @param message the exception message to use if the assertion fails
   * @throws IllegalArgumentException if the text is null or empty or longer than max
   */
  public static void hasMaxText(String text, int max, String message) {
    Assert.hasText(text, message);
    Assert.isTrue(text.length() <= max, message);
  }

  /**
   * Assert that the given collection has minimum size.
   *
   * @param collection the collection to check
   * @param min the minimum size
   * @param message the exception message to use if the assertion fails
   */
  public static <T> void hasMinSize(Collection<T> collection, int min, String message) {
    Assert.notNull(collection, message);
    Assert.isTrue(!collection.isEmpty() && collection.size() >= min, message);
  }

  /**
   * Assert that the given collection has maximum size.
   *
   * @param collection the collection to check
   * @param max the maximum size
   * @param message the exception message to use if the assertion fails
   */
  public static <T> void hasMaxSize(Collection<T> collection, int max, String message) {
    Assert.notNull(collection, message);
    Assert.isTrue(!collection.isEmpty() && collection.size() <= max, message);
  }

  /** Assert that the given text has maximum length or is null. */
  public static void hasMaxTextOrNull(String text, int max, String message) {
    if (text == null) {
      return;
    }

    hasMaxText(text, max, message);
  }

  /** Assert that the given collection has maximum size or is null. */
  public static <T> void hasMaxSizeOrNull(Collection<T> collection, int max, String message) {
    if (collection == null) {
      return;
    }

    hasMaxSize(collection, max, message);
  }
}
