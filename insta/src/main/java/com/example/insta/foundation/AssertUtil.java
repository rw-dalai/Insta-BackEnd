package com.example.insta.foundation;

import static java.lang.String.format;

import java.util.Collection;
import org.springframework.util.Assert;

// WHAT IS LAZY EVALUATION ?
// ----------------------------------------------------------------
// Assert.hasText(email, () -> format(isValidEmailMsg, name));
// () -> format(isValidEmailMsg, name)` is a lambda expression.
// It's a function that is called when the assertion fails.
// We don't want to call format() when the assertion succeeds.
// Why ? Because format() is an expensive operation.
// That's called `lazy evaluation`.

// JAVA GENERICS
// ----------------------------------------------------------------
// public static <C extends Collection<?>> C hasMaxSizeOrNull(C c, int max, String name)
// C extends Collection<?> this means that C is any type of Collection of any type.
// Video for Generics
// https://www.youtube.com/watch?v=K1iu1kXkVoA

/**
 * Assertion utility class that assists in validating arguments. We also use Spring's Assert class
 * here and in our domain models.
 */
public abstract class AssertUtil {
  // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression
  // A simple email pattern that is not 100% correct but good enough for our use case.
  private static final String emailPattern = "^(.+)@(\\S+)$";

  private static final String isNotNullMsg = "%s must not be null";
  private static final String isValidEmailMsg = "%s must be a valid email address";
  private static final String hasMinTextMsg = "%s must be more or equal %d character";
  private static final String hasMaxTextMsg = "%s must be less or equal %d character";
  private static final String hasMinSizeMsg = "%s must be more or equal %d";
  private static final String hasMaxSizeMsg = "%s must be less or equal %d";

  /**
   * Assert that the given object is not null.
   *
   * @param obj the object to check
   * @param name the attribute name to use if the assertion fails
   * @return the object
   */
  public static <T> T isNotNull(T obj, String name) {
    Assert.notNull(obj, () -> format(isNotNullMsg, name));
    return obj;
  }

  /**
   * Assert that the given email is valid.
   *
   * @param email the email to check
   * @param name the attribute name to use if the assertion fails
   * @return the email
   */
  public static String isValidEmail(String email, String name) {
    Assert.hasText(email, () -> format(isValidEmailMsg, name));
    Assert.isTrue(email.matches(emailPattern), () -> format(isValidEmailMsg, name));
    return email;
  }

  /**
   * Assert that the given value has minimum size.
   *
   * @param min the minimum size
   * @param name the attribute name to use if the assertion fails
   * @return the value
   */
  public static long hasMinSize(long value, long min, String name) {
    Assert.isTrue(value >= min, () -> format(hasMinSizeMsg, name, min));
    return value;
  }

  /**
   * Assert that the given integer has maximum size.
   *
   * @param max the maximum size
   * @param name the attribute name to use if the assertion fails
   * @return the value
   */
  public static long hasMaxSize(long value, long max, String name) {
    Assert.isTrue(value <= max, () -> format(hasMaxSizeMsg, name, max));
    return value;
  }

  /**
   * Assert that the given text has minimum length.
   *
   * @param text the text to check
   * @param min the minimum length
   * @param name the attribute name to use if the assertion fails
   * @throws IllegalArgumentException if the text is null or empty or shorter than min
   * @return the text
   */
  public static String hasMinText(String text, int min, String name) {
    Assert.hasText(text, () -> format(hasMinTextMsg, name, min));
    Assert.isTrue(text.length() >= min, () -> format(hasMinTextMsg, name, min));
    return text;
  }

  /**
   * Assert that the given text has maximum length.
   *
   * @param text the text to check
   * @param max the maximum length
   * @param name the attribute name to use if the assertion fails
   * @throws IllegalArgumentException if the text is null or empty or longer than max
   * @return the text
   */
  public static String hasMaxText(String text, int max, String name) {
    Assert.hasText(text, () -> format(hasMaxTextMsg, name, max));
    Assert.isTrue(text.length() <= max, () -> format(hasMaxTextMsg, name, max));
    return text;
  }

  /**
   * Assert that the given collection has minimum size.
   *
   * @param c the collection to check
   * @param min the minimum size
   * @param name the attribute name to use if the assertion fails
   * @return the collection
   */
  public static <C extends Collection<?>> C hasMinSize(C c, int min, String name) {
    Assert.notNull(c, () -> format(hasMinSizeMsg, name, min));
    Assert.isTrue(!c.isEmpty() && c.size() >= min, () -> format(hasMinSizeMsg, name, min));
    return c;
  }

  /**
   * Assert that the given collection has maximum size.
   *
   * @param c the collection to check
   * @param max the maximum size
   * @param name the attribute name to use if the assertion fails
   * @return the collection
   */
  public static <C extends Collection<?>> C hasMaxSize(C c, int max, String name) {
    Assert.notNull(c, () -> format(hasMaxSizeMsg, name, max));
    Assert.isTrue(!c.isEmpty() && c.size() <= max, () -> format(hasMaxSizeMsg, name, max));
    return c;
  }

  /** Assert that the given text has maximum length or is null. */
  public static String hasMaxTextOrNull(String text, int max, String name) {
    if (text != null) {
      hasMaxText(text, max, name);
    }
    return text;
  }

  /** Assert that the given collection has maximum size or is null. */
  public static <C extends Collection<?>> C hasMaxSizeOrNull(C c, int max, String name) {
    if (c != null) {
      hasMaxSize(c, max, name);
    }
    return c;
  }
}
